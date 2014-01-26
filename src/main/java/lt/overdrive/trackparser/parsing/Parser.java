package lt.overdrive.trackparser.parsing;

import lt.overdrive.trackparser.domain.Trail;
import lt.overdrive.trackparser.parsing.gpx.GpxParser;
import lt.overdrive.trackparser.parsing.tcx.TcxParser;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import java.io.File;

import static lt.overdrive.trackparser.parsing.GpsFileType.*;

public class Parser {
    private File file;

    private Parser(File file) {
        this.file = file;
    }

    public static Trail parseFile(File file) throws ParserException {
        Parser parser = new Parser(file);

        GpsFileType type = parser.guessFileType();

        return parser.parserForType(type).parse(file);
    }

    private GpsFileParser parserForType(GpsFileType type) throws UnrecognizedFileException {
        switch (type) {
            case TCX:
                return new TcxParser();
            case GPX:
                return new GpxParser();
            case UNKNOWN:
            default:
                throw new UnrecognizedFileException("File " + file + " was not recognized to be of supported type [TCX, GPX]");
        }
    }

    private GpsFileType guessFileType() throws ParserException {
        if (isTcxFile()) return TCX;
        else if (isGpxFile()) return GPX;
        else return UNKNOWN;
    }

    private boolean isGpxFile() throws ParserException {
        Schema schema = new GpxParser().getSchema();
        return isValidSchemaDocument(schema);
    }

    private boolean isTcxFile() throws ParserException {
        Schema schema = new TcxParser().getSchema();
        return isValidSchemaDocument(schema);
    }

    private boolean isValidSchemaDocument(Schema schema) {
        Validator validator = schema.newValidator();

        Source source = new StreamSource(file);
        try {
            validator.validate(source);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
