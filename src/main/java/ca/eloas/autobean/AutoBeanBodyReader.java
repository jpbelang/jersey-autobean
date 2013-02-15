package ca.eloas.autobean;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.google.web.bindery.autobean.vm.AutoBeanFactorySource;
import com.sun.jersey.core.util.ReaderWriter;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

/**
 * @author JP
 */
@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class AutoBeanBodyReader implements MessageBodyReader<Object> {

    private AutoBeanFactory factory = AutoBeanFactorySource.create(GenericAutoBeanFactory.class);

    public AutoBeanBodyReader() {
    }

    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {

        return type.isInterface();
    }

    public Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {

        String data = ReaderWriter.readFromAsString(entityStream, mediaType);
        AutoBean s = AutoBeanCodex.decode(factory, type, data);
        return s.as();
    }

}
