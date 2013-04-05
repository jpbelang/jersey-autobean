package ca.eloas.autobean;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanVisitor;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author JP
 */
public class EnsureCollectionExistsVisitor extends AutoBeanVisitor {

    @Override
    public boolean visitCollectionProperty(String propertyName, AutoBean<Collection<?>> value, CollectionPropertyContext ctx) {

        if ( value == null ) {

            ctx.set(new ArrayList());
        }

        return true;
    }
}
