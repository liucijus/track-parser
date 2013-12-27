package lt.overdrive.trackparser.processing.domain;

import lt.overdrive.trackparser.domain.GpsTrackPoint;

public class SegmentPoint {
    private GpsTrackPoint point;

    public SegmentPoint(GpsTrackPoint point) {
        this.point = point;
    }

    public GpsTrackPoint getPoint() {
        return point;
    }
}
