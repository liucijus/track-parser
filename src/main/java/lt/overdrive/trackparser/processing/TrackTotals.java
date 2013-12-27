package lt.overdrive.trackparser.processing;

import org.joda.time.Seconds;

public class TrackTotals {
    private double distance = 0;
    private Seconds time;
    private Double ascent;
    private Double descent;
    private Double maxSpeed;
    private Double minSpeed;

    public TrackTotals(double distance, Seconds time, Double ascent, Double descent, Double maxSpeed, Double minSpeed) {
        this.distance = distance;
        this.time = time;
        this.ascent = ascent;
        this.descent = descent;
        this.maxSpeed = maxSpeed;
        this.minSpeed = minSpeed;
    }

    public double getDistance() {
        return distance;
    }

    public Seconds getTime() {
        return time;
    }

    public Double getAverageSpeedInMetersPerSecond() {
        return time != null ? distance / time.getSeconds() : null;
    }

    public Double getAscent() {
        return ascent;
    }

    public Double getDescent() {
        return descent;
    }

    public Double getMaxSpeed() {
        return maxSpeed;
    }

    public Double getMinSpeed() {
        return minSpeed;
    }
}
