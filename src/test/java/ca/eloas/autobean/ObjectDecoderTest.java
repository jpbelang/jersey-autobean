package ca.eloas.autobean;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author JP
 */
public class ObjectDecoderTest {

    @Test
    public void testSimple() throws Exception {

        JSONObject jo = new JSONObject();
        jo.put("@type", SubOne.class.getName());
        jo.put("name", "dave");
        jo.put("one", 3);

        ObjectDecoder od = new ObjectDecoder();
        SubOne so = od.createObjectFromString(SubOne.class, jo.toString());

        assertTrue(so instanceof SubOne);
        assertEquals("dave", so.getName());
        assertEquals(3, so.getOne());
    }

    @Test
    public void hintOverClass() throws Exception {

        JSONObject jo = new JSONObject();
        jo.put("@type", SubOne.class.getName());
        jo.put("name", "dave");
        jo.put("one", 3);

        ObjectDecoder od = new ObjectDecoder();
        SubOne so = od.createObjectFromString(Top.class, jo.toString());

        assertTrue(so instanceof SubOne);
        assertEquals("dave", so.getName());
        assertEquals(3, so.getOne());
    }

    @Test
    public void noTypeGoodHint() throws Exception {

        JSONObject jo = new JSONObject();
        jo.put("name", "dave");
        jo.put("one", 3);

        ObjectDecoder od = new ObjectDecoder();
        SubOne so = od.createObjectFromString(SubOne.class, jo.toString());

        assertTrue(so instanceof SubOne);
        assertEquals("dave", so.getName());
        assertEquals(3, so.getOne());
    }

    @Test
    public void noTypeTopHint() throws Exception {

        JSONObject jo = new JSONObject();
        jo.put("name", "dave");
        jo.put("one", 3);

        ObjectDecoder od = new ObjectDecoder();
        Top so = od.createObjectFromString(Top.class, jo.toString());

        assertTrue(so instanceof Top);
        assertEquals("dave", so.getName());
    }


}
