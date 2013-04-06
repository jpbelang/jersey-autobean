package ca.eloas.autobean;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.google.web.bindery.autobean.shared.Splittable;
import com.google.web.bindery.autobean.vm.impl.JsonSplittable;

/**
 * @author JP
 */
public class PolymorphicAutoBeanFactory implements AutoBeanFactory {

    private AutoBeanFactory delegate;

    @Override
    public <T> AutoBean<T> create(Class<T> clazz) {
        AutoBean<T> bean = delegate.create(clazz);
        return AutoBeanCodex.decode(delegate, clazz, "{\"@type\": \"" + clazz.getName() + "\"}");
    }

    @Override
    public <T, U extends T> AutoBean<T> create(Class<T> clazz, U delegate) {
        return this.delegate.create(clazz, delegate);
    }
}
