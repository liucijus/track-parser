package lt.overdrive.trackparser.parsing;

public class ParserException extends Exception {
    public ParserException(String message) {
        super(message);
    }

    public ParserException(String message, Throwable e) {
        super(message, e);
    }
}
