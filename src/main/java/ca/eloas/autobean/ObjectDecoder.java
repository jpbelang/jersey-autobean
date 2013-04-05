package ca.eloas.autobean;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;
import com.google.web.bindery.autobean.shared.AutoBeanVisitor;
import com.google.web.bindery.autobean.vm.AutoBeanFactorySource;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author JP
 */
public class ObjectDecoder {

    private AutoBeanFactory factory = AutoBeanFactorySource.create(GenericAutoBeanFactory.class);

    public <T, S extends T> S createObjectFromString(Class<T> hint, String data) throws IOException {

        try {
            Class targetClass = hint;
            JSONObject jo = new JSONObject(data);
            String type = jo.optString("@type");
            if (type != null && type.length() > 0) {

                targetClass = Class.forName(type);
            }

            AutoBean s = AutoBeanCodex.decode(factory, targetClass, data);
            s.accept(new PolymorphicAutobeanVisitor());
            return (S) s.as();
        } catch (JSONException e) {

            throw new IOException("unable to decode object", e);
        } catch (ClassNotFoundException e) {

            throw new IOException("class not found", e);
        }
    }

    private class PolymorphicAutobeanVisitor extends AutoBeanVisitor {


        @Override
        public boolean visitCollectionProperty(String propertyName, AutoBean<Collection<?>> value, CollectionPropertyContext ctx) {

            if ( value == null ) {

                return true;
            }

            Iterator it = value.as().iterator();
            List<Object> list = new ArrayList<Object>();
            while (it.hasNext()) {
                Object next = it.next();

                AutoBean autoNext = AutoBeanUtils.getAutoBean(next);
                try {
                    JSONObject sp = new JSONObject(next.toString());
                    String type = sp.getString("@type");
                    if (value.getType().getName().equals(type)) {

                        list.add(autoNext);
                    } else {
                        AutoBean newAutoBean = factory.create(Class.forName(type));
                        list.add(newAutoBean.as());
                    }
                } catch (Exception e) {

                    continue;
                }
            }

            ctx.set(list);
            return true;
        }

        @Override
        public boolean visitReferenceProperty(String propertyName, AutoBean<?> value, PropertyContext ctx) {

            try {
                JSONObject sp = new JSONObject(value.as().toString());
                String type = sp.getString("@type");
                if (value.getType().getName().equals(type)) {

                    return true;
                } else {
                    AutoBean newAutoBean = factory.create(Class.forName(type));
                    ctx.set(newAutoBean.as());
                    return true;
                }
            } catch (Exception e) {

                return true;
            }
        }
    }
}
