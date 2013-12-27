package lt.overdrive.trackparser.parsing.gpx;

import lt.overdrive.trackparser.parsing.ParserException;

public class GpxParserException extends ParserException {
    public GpxParserException(String message) {
        super(message);
    }

    public GpxParserException(String message, Throwable e) {
        super(message, e);
    }
}
