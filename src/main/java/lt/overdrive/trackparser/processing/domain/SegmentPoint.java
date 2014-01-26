package lt.overdrive.trackparser.processing.domain;

import lt.overdrive.trackparser.domain.TrackPoint;

public class SegmentPoint {
    private TrackPoint point;

    public SegmentPoint(TrackPoint point) {
        this.point = point;
    }

    public TrackPoint getPoint() {
        return point;
    }
}
