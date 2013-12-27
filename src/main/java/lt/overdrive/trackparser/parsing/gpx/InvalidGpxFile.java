package lt.overdrive.trackparser.parsing.gpx;

public class InvalidGpxFile extends GpxParserException {
    public InvalidGpxFile(String message) {
        super(message);
    }

    public InvalidGpxFile(String message, Throwable e) {
        super(message, e);
    }
}
