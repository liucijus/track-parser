package lt.overdrive.trackparser.utils;

import lt.overdrive.trackparser.parsing.tcx.TcxParserException;
import org.xml.sax.SAXException;

import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.net.URISyntaxException;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

public class ResourceUtils {
    public static File getFile(String name) {
        try {
            return new File(Thread.currentThread().getContextClassLoader().getResource(name).toURI());
        } catch (URISyntaxException e) {
            return null;
        }
    }

    public static Schema loadSchema(String name) throws TcxParserException {
        SchemaFactory sf = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
        try {
            return sf.newSchema(getFile(name));
        } catch (SAXException se) {
            throw new TcxParserException("Schema loading failed.", se);
        }
    }
}
