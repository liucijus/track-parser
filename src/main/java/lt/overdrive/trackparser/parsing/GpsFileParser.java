package lt.overdrive.trackparser.parsing;

import lt.overdrive.trackparser.domain.GpsTrail;

import javax.xml.validation.Schema;
import java.io.File;

public interface GpsFileParser {
    GpsTrail parse(File file) throws ParserException;
    Schema getSchema() throws ParserException;
}
