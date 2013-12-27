package lt.overdrive.trackparser.parsing.tcx;

public class InvalidTxcFile extends TcxParserException {
    public InvalidTxcFile(String message, Throwable e) {
        super(message, e);
    }
}
