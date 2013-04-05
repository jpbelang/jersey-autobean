package ca.eloas.autobean;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.google.web.bindery.autobean.vm.AutoBeanFactorySource;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author JP
 */
public class ObjectEncoderTest {

    private AutoBeanFactory factory = AutoBeanFactorySource.create(GenericAutoBeanFactory.class);

    @Test
    public void testSimple() throws Exception {

        AutoBean<SubOne> so = factory.create(SubOne.class);
        so.as().setName("dave");
        ObjectEncoder od = new ObjectEncoder();
        String s = od.encode(so);

        JSONObject jo = new JSONObject(s);
        assertEquals("dave", jo.get("name"));
        assertEquals(SubOne.class.getName(), jo.get("@type"));
    }


}
