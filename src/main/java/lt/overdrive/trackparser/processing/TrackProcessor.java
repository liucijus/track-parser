package lt.overdrive.trackparser.processing;

import lt.overdrive.trackparser.domain.Track;
import lt.overdrive.trackparser.domain.TrackPoint;
import lt.overdrive.trackparser.processing.domain.Segment;
import lt.overdrive.trackparser.processing.domain.SegmentPoint;
import lt.overdrive.trackparser.processing.domain.SegmentTrack;
import org.joda.time.Seconds;

import java.util.*;

public class TrackProcessor {
    private static final double EARTH_RADIUS_IN_M = 6372.8 * 1000;
    private final SegmentTrack segmentTrack;
    private Track track;
    private Set<TrackPoint> pointsWithoutTime = new HashSet<>();
    private Set<TrackPoint> pointsWithoutAltitude = new HashSet<>();

    public TrackProcessor(final Track track) {
        this.track = track;
        this.segmentTrack = convert();
    }

    private static TrackRectangle calculateTrackRectangle(List<TrackPoint> points) {
        double maxLatitude = 0;
        double maxLongitude = 0;
        double minLatitude = Double.MAX_VALUE;
        double minLongitude = Double.MAX_VALUE;
        for (TrackPoint point : points) {
            maxLatitude = Math.max(maxLatitude, point.getLatitude());
            minLatitude = Math.min(minLatitude, point.getLatitude());
            maxLongitude = Math.max(maxLongitude, point.getLongitude());
            minLongitude = Math.min(minLongitude, point.getLongitude());
        }
        return new TrackRectangle(new TrackPoint(maxLatitude, maxLongitude), new TrackPoint(minLatitude, minLongitude));
    }

    public static TrackRectangle calculateRectangle(List<Track> tracks) {
        ArrayList<TrackPoint> points = new ArrayList<>();
        for (Track track : tracks) {
            points.addAll(track.getPoints());
        }
        return calculateTrackRectangle(points);
    }

    public TrackTotals calculateTotals() {
        double distance = 0;
        double ascent = 0;
        double descent = 0;

        for (Segment segment : segmentTrack.getSegments()) {
            distance += segment.getDistance();

            if (pointsWithoutAltitude.isEmpty()) {
                ascent += segment.getAscent();
                descent += segment.getDescent();
            }
        }

        return createTotals(distance, ascent, descent, null, null);
    }

    private TrackTotals createTotals(double distance, Double ascent, Double descent, Double maxSpeed, Double minSpeed) {
        if (segmentTrack.getSegments().isEmpty()) {
            maxSpeed = 0D;
            minSpeed = 0D;
        } else {
            if (pointsWithoutTime.isEmpty()) {
                SortedSet<Segment> sortedBySpeed = getTrackSegmentsSortedBySpeed();
                maxSpeed = sortedBySpeed.last().getSpeed();
                minSpeed = sortedBySpeed.first().getSpeed();
            }

            if (!pointsWithoutAltitude.isEmpty()) {
                ascent = null;
                descent = null;
            }
        }

        return new TrackTotals(distance, calculateTotalTime(), ascent, descent, maxSpeed, minSpeed);
    }

    private SortedSet<Segment> getTrackSegmentsSortedBySpeed() {
        SortedSet<Segment> sortedBySpeed = new TreeSet<>(new Comparator<Segment>() {
            @Override
            public int compare(Segment o1, Segment o2) {
                return Double.compare(o1.getSpeed(), o2.getSpeed());
            }
        });
        sortedBySpeed.addAll(segmentTrack.getSegments());
        return sortedBySpeed;
    }

    private SegmentTrack convert() {
        List<Segment> segments = new ArrayList<>();
        List<SegmentPoint> segmentPoints = new ArrayList<>();
        List<TrackPoint> points = track.getPoints();
        for (ListIterator<TrackPoint> iterator = points.listIterator(); iterator.hasNext(); ) {
            processSegment(segments, points, iterator.previousIndex(), iterator.nextIndex());
            processPoint(segmentPoints, iterator.next());
        }
        return new SegmentTrack(segments, segmentPoints);
    }

    private void processPoint(List<SegmentPoint> segmentPoints, TrackPoint point) {
        populateWithoutTimeIfNeeded(point);
        populateWithoutAltitudeIfNeeded(point);

        segmentPoints.add(new SegmentPoint(point));
    }

    private void processSegment(List<Segment> segments, List<TrackPoint> points, int previousIndex, int nextIndex) {
        if (validIndexes(points, previousIndex, nextIndex)) {
            Segment segment = createSegment(points, previousIndex, nextIndex);
            segments.add(segment);
        }
    }

    private void populateWithoutAltitudeIfNeeded(TrackPoint point) {
        if (point.getAltitude() == null) pointsWithoutAltitude.add(point);
    }

    private void populateWithoutTimeIfNeeded(TrackPoint point) {
        if (point.getTime() == null) pointsWithoutTime.add(point);
    }

    private Segment createSegment(List<TrackPoint> points, int previousIndex, int nextIndex) {
        TrackPoint point1 = points.get(previousIndex);
        TrackPoint point2 = points.get(nextIndex);
        Segment segment = new Segment(point1, point2);
        segment.setDistance(calculateDistance(point1, point2));
        return segment;
    }

    private boolean validIndexes(List<TrackPoint> points, int previousIndex, int nextIndex) {
        return previousIndex != -1 && nextIndex < points.size();
    }

    private Seconds calculateTotalTime() {
        List<TrackPoint> points = track.getPoints();
        int size = points.size();
        return size > 0 ? calculateTimeBetweenPoints(points.get(0), points.get(size - 1)) : Seconds.ZERO;
    }

    private Seconds calculateTimeBetweenPoints(TrackPoint firstPoint, TrackPoint lastPoint) {
        return pointsHaveTime(firstPoint, lastPoint) ? calculateTime(firstPoint, lastPoint) : null;
    }

    private boolean pointsHaveTime(TrackPoint firstPoint, TrackPoint lastPoint) {
        return firstPoint.getTime() != null && lastPoint.getTime() != null;
    }

    private Seconds calculateTime(TrackPoint point1, TrackPoint point2) {
        return Seconds.secondsBetween(point1.getTime(), point2.getTime());
    }

    /**
     * Calculates distance using Haversine formula.
     *
     * @see <a href="http://en.wikipedia.org/wiki/Haversine_formula">Haversine formula</a>
     */
    private double calculateDistance(TrackPoint point1, TrackPoint point2) {
        double latitude1 = point1.getLatitude();
        double latitude2 = point2.getLatitude();
        double latitudeDistance = Math.toRadians(latitude2 - latitude1);

        double longitude1 = point1.getLongitude();
        double longitude2 = point2.getLongitude();
        double longitudeDistance = Math.toRadians(longitude2 - longitude1);

        double a = Math.pow(Math.sin(latitudeDistance / 2), 2) +
                Math.pow(Math.sin(longitudeDistance / 2), 2) *
                        Math.cos(Math.toRadians(latitude1)) *
                        Math.cos(Math.toRadians(latitude2));
        double c = 2 * Math.asin(Math.sqrt(a));

        return EARTH_RADIUS_IN_M * c;
    }

    public SegmentTrack getSegmentTrack() {
        return segmentTrack;
    }

    public TrackRectangle calculateRectangle() {
        if (track.getPoints().isEmpty()) return null;
        return calculateTrackRectangle(track.getPoints());
    }
}
