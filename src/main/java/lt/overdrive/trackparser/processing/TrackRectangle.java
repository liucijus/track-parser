package lt.overdrive.trackparser.processing;

import lt.overdrive.trackparser.domain.TrackPoint;

public class TrackRectangle {
    private TrackPoint topRightPoint;
    private TrackPoint bottomLeftPoint;

    public TrackRectangle(TrackPoint topRightPoint, TrackPoint bottomLeftPoint) {
        this.topRightPoint = topRightPoint;
        this.bottomLeftPoint = bottomLeftPoint;
    }

    public TrackPoint getTopRightPoint() {
        return topRightPoint;
    }

    public TrackPoint getBottomLeftPoint() {
        return bottomLeftPoint;
    }

    public TrackPoint getCenterPoint() {
        double topLatitude = topRightPoint.getLatitude();
        double latitude = topLatitude - ((topLatitude - bottomLeftPoint.getLatitude()) / 2);
        double topLongitude = topRightPoint.getLongitude();
        double longitude = topLongitude - ((topLongitude - bottomLeftPoint.getLongitude()) / 2);

        return new TrackPoint(latitude, longitude);
    }
}
