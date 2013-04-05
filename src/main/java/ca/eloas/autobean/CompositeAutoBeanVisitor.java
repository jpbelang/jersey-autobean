package ca.eloas.autobean;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanVisitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author JP
 */
public class CompositeAutoBeanVisitor extends AutoBeanVisitor {

    private List<AutoBeanVisitor> visitors = new ArrayList<AutoBeanVisitor>();

    public CompositeAutoBeanVisitor(List<AutoBeanVisitor> visitors) {
        this.visitors = visitors;
    }

    public CompositeAutoBeanVisitor(AutoBeanVisitor... visitors) {

        this(Arrays.asList(visitors));
    }

    @Override
    public void endVisit(AutoBean<?> bean, Context ctx) {

        for (AutoBeanVisitor visitor : visitors) {
            visitor.endVisit(bean, ctx);
        }
    }

    @Override
    public void endVisitCollectionProperty(String propertyName, AutoBean<Collection<?>> value, CollectionPropertyContext ctx) {

        for (AutoBeanVisitor visitor : visitors) {
            visitor.endVisitCollectionProperty(propertyName, value, ctx);
        }
    }

    @Override
    public void endVisitMapProperty(String propertyName, AutoBean<Map<?, ?>> value, MapPropertyContext ctx) {

        for (AutoBeanVisitor visitor : visitors) {
            visitor.endVisitMapProperty(propertyName, value, ctx);
        }
    }

    @Override
    public void endVisitReferenceProperty(String propertyName, AutoBean<?> value, PropertyContext ctx) {

        for (AutoBeanVisitor visitor : visitors) {
            visitor.endVisitReferenceProperty(propertyName, value, ctx);
        }
    }

    @Override
    public void endVisitValueProperty(String propertyName, Object value, PropertyContext ctx) {

        for (AutoBeanVisitor visitor : visitors) {
            visitor.endVisitValueProperty(propertyName, value, ctx);
        }

    }

    @Override
    public boolean visit(AutoBean<?> bean, Context ctx) {
        for (AutoBeanVisitor visitor : visitors) {
            boolean result = visitor.visit(bean, ctx);
            if (result == false) {

                return false;
            }
        }

        return true;
    }

    @Override
    public boolean visitCollectionProperty(String propertyName, AutoBean<Collection<?>> value, CollectionPropertyContext ctx) {
        for (AutoBeanVisitor visitor : visitors) {
            boolean result = visitor.visitCollectionProperty(propertyName, value, ctx);
            if (result == false) {

                return false;
            }
        }

        return true;
    }

    @Override
    public boolean visitMapProperty(String propertyName, AutoBean<Map<?, ?>> value, MapPropertyContext ctx) {

        for (AutoBeanVisitor visitor : visitors) {
            boolean result = visitor.visitMapProperty(propertyName, value, ctx);
            if (result == false) {

                return false;
            }
        }

        return true;
    }

    @Override
    public boolean visitReferenceProperty(String propertyName, AutoBean<?> value, PropertyContext ctx) {

        for (AutoBeanVisitor visitor : visitors) {
            boolean result = visitor.visitReferenceProperty(propertyName, value, ctx);
            if (result == false) {

                return false;
            }
        }

        return true;
    }

    @Override
    public boolean visitValueProperty(String propertyName, Object value, PropertyContext ctx) {
        for (AutoBeanVisitor visitor : visitors) {
            boolean result = visitor.visitValueProperty(propertyName, value, ctx);
            if (result == false) {

                return false;
            }
        }

        return true;
    }
}
