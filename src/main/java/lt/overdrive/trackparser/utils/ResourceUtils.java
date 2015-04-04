package lt.overdrive.trackparser.utils;

import lt.overdrive.trackparser.parsing.tcx.TcxParserException;
import org.xml.sax.SAXException;

import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

public class ResourceUtils {
    public static URL getURL(String name) {
            return Thread.currentThread().getContextClassLoader().getResource(name);
    }

    public static Schema loadSchema(String name) throws TcxParserException {
        SchemaFactory sf = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
        try {
            return sf.newSchema(getURL(name));
        } catch (SAXException se) {
            throw new TcxParserException("Schema loading failed.", se);
        }
    }
}
