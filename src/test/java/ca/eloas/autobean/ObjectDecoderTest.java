package ca.eloas.autobean;

import org.json.JSONArray;
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
    public void testReferenceType() throws Exception {

        JSONObject jo = new JSONObject();
        jo.put("@type", SubOne.class.getName());
        jo.put("name", "dave");
        jo.put("one", 3);

        JSONObject jo2 = new JSONObject();
        jo2.put("@type", SubOne.class.getName());
        jo2.put("name", "paul");
        jo2.put("one", 4);

        jo.put("child", jo2);

        ObjectDecoder od = new ObjectDecoder();
        SubOne so = od.createObjectFromString(SubOne.class, jo.toString());

        assertTrue(so instanceof SubOne);
        assertEquals("dave", so.getName());
        assertEquals(3, so.getOne());

        Top child = so.getChild();
        assertTrue(child instanceof SubOne);
    }

    @Test
    public void testCollectionType() throws Exception {

        JSONObject jo = new JSONObject();
        jo.put("@type", SubOne.class.getName());
        jo.put("name", "dave");
        jo.put("one", 3);

        JSONObject jo2 = new JSONObject();
        jo2.put("@type", SubOne.class.getName());
        jo2.put("name", "paul");
        jo2.put("one", 4);

        JSONArray ja = new JSONArray();
        ja.put(jo2);
        jo.put("list", ja);

        ObjectDecoder od = new ObjectDecoder();
        SubOne so = od.createObjectFromString(SubOne.class, jo.toString());

        assertTrue(so instanceof SubOne);
        assertEquals("dave", so.getName());
        assertEquals(3, so.getOne());

        Top child = so.getList().get(0);
        assertTrue(child instanceof SubOne);
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

    @Test
    public void nonEmptyCollections() throws Exception {

        JSONObject jo = new JSONObject();
        jo.put("@type", SubOne.class.getName());

        ObjectDecoder od = new ObjectDecoder();
        SubOne so = od.createObjectFromString(SubOne.class, jo.toString());

        assertNotNull(so.getList());
    }


}
