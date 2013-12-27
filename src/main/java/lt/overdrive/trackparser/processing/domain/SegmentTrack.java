package lt.overdrive.trackparser.processing.domain;

import java.util.List;

public class SegmentTrack {
    private List<Segment> segments;
    private List<SegmentPoint> segmentPoints;

    public SegmentTrack(List<Segment> segments, List<SegmentPoint> segmentPoints) {
        this.segments = segments;
        this.segmentPoints = segmentPoints;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public List<SegmentPoint> getSegmentPoints() {
        return segmentPoints;
    }
}
