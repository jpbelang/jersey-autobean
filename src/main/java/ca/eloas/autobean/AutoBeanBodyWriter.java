package ca.eloas.autobean;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;
import com.google.web.bindery.autobean.shared.Splittable;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

/**
 * @author JP
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class AutoBeanBodyWriter implements MessageBodyWriter<Object> {


    public long getSize(Object messageBean, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {

        return Proxy.isProxyClass(type);
    }

    public void writeTo(Object messageBean, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {

        AutoBean b = AutoBeanUtils.getAutoBean(messageBean);
        if ( b == null ) {

            throw new IOException(messageBean + " is not an AutoBean");
        }

        Splittable s = AutoBeanCodex.encode(b);
        entityStream.write(s.getPayload().getBytes());
    }
}
