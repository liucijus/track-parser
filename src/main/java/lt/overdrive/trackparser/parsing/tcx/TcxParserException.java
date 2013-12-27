package lt.overdrive.trackparser.parsing.tcx;

import lt.overdrive.trackparser.parsing.ParserException;

public class TcxParserException extends ParserException {
    public TcxParserException(String message) {
        super(message);
    }

    public TcxParserException(String message, Throwable e) {
        super(message, e);
    }
}
