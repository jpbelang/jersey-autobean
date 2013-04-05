package ca.eloas.autobean;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.google.web.bindery.autobean.vm.AutoBeanFactorySource;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author JP
 */
public class ObjectDecoder {

    private AutoBeanFactory factory = AutoBeanFactorySource.create(GenericAutoBeanFactory.class);

    public <T,S extends T> S createObjectFromString(Class<T> hint, String data) throws IOException {

        try {
            Class targetClass = hint;
            JSONObject jo = new JSONObject(data);
            String type = jo.optString("@type");
            if (type != null && type.length() > 0) {

                targetClass = Class.forName(type);

            }

            AutoBean s = AutoBeanCodex.decode(factory, targetClass, data);

            return (S) s.as();
        } catch (JSONException e) {

            throw new IOException("unable to decode object", e);
        } catch (ClassNotFoundException e) {

            throw new IOException("class not found", e);
        }
    }
}
