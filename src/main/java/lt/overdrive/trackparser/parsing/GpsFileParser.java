package lt.overdrive.trackparser.parsing;

import lt.overdrive.trackparser.domain.Trail;

import javax.xml.validation.Schema;
import java.io.File;

public interface GpsFileParser {
    Trail parse(File file) throws ParserException;
    Schema getSchema() throws ParserException;
}
