package ca.eloas.autobean;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.Splittable;
import com.google.web.bindery.autobean.vm.impl.JsonSplittable;

/**
 * @author JP
 */
public class ObjectEncoder {

    public String encode(AutoBean b) {

        Splittable s = AutoBeanCodex.encode(b);
        Class c = b.getType();

        Splittable split = JsonSplittable.create("\"" + c.getName() + "\"");
        split.assign(s, "@type");

        return s.getPayload();
    }
}
