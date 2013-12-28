package lt.overdrive.trackparser.processing;

import lt.overdrive.trackparser.domain.GpsTrackPoint;

public class TrackRectangle {
    private GpsTrackPoint topRightPoint;
    private GpsTrackPoint bottomLeftPoint;

    public TrackRectangle(GpsTrackPoint topRightPoint, GpsTrackPoint bottomLeftPoint) {
        this.topRightPoint = topRightPoint;
        this.bottomLeftPoint = bottomLeftPoint;
    }

    public GpsTrackPoint getTopRightPoint() {
        return topRightPoint;
    }

    public GpsTrackPoint getBottomLeftPoint() {
        return bottomLeftPoint;
    }

    public GpsTrackPoint getCenterPoint() {
        double topLatitude = topRightPoint.getLatitude();
        double latitude = topLatitude - ((topLatitude - bottomLeftPoint.getLatitude()) / 2);
        double topLongitude = topRightPoint.getLongitude();
        double longitude = topLongitude - ((topLongitude - bottomLeftPoint.getLongitude()) / 2);

        return new GpsTrackPoint(latitude, longitude);
    }
}
