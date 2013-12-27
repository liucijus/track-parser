package lt.overdrive.trackparser.parsing;

public class UnrecognizedFileException extends ParserException {
    public UnrecognizedFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnrecognizedFileException(String message) {
        super(message);
    }
}
