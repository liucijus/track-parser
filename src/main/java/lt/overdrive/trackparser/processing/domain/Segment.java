package lt.overdrive.trackparser.processing.domain;

import lt.overdrive.trackparser.domain.GpsTrackPoint;
import org.joda.time.Seconds;

public class Segment {
    private final GpsTrackPoint point1;
    private final GpsTrackPoint point2;
    private double distance;

    public Segment(GpsTrackPoint point1, GpsTrackPoint point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Segment that = (Segment) o;

        if (!point1.equals(that.point1)) return false;
        if (!point2.equals(that.point2)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = point1.hashCode();
        result = 31 * result + point2.hashCode();
        return result;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getAscent() {
        double difference = point2.getAltitude() - point1.getAltitude();
        return difference > 0 ? difference : 0;
    }

    public double getDescent() {
        double difference = point2.getAltitude() - point1.getAltitude();
        return difference > 0 ? 0 : Math.abs(difference);
    }

    public Seconds getTime() {
        return Seconds.secondsBetween(point1.getTime(), point2.getTime());
    }

    public double getSpeed() {
        return distance / getTime().getSeconds();
    }
}
