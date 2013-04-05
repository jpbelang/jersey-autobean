package ca.eloas.autobean;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;
import com.google.web.bindery.autobean.shared.AutoBeanVisitor;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
* @author JP
*/
class PolymorphicAutobeanVisitor extends AutoBeanVisitor {


    private AutoBeanFactory factory;

    public PolymorphicAutobeanVisitor(AutoBeanFactory objectDecoder) {
        this.factory = objectDecoder;
    }

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
