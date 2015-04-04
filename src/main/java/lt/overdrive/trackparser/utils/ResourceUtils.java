package lt.overdrive.trackparser.utils;

import lt.overdrive.trackparser.parsing.tcx.TcxParserException;
import org.xml.sax.SAXException;

import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.net.URL;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

public class ResourceUtils {
    public static Schema loadSchema(String name) throws TcxParserException {
        SchemaFactory sf = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
        try {
            return sf.newSchema(getUrl(name));
        } catch (SAXException se) {
            throw new TcxParserException("Schema loading failed.", se);
        }
    }

    private static URL getUrl(String name) {
        return Thread.currentThread().getContextClassLoader().getResource(name);
    }
}
