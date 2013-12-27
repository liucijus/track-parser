package lt.overdrive.trackparser.domain;

import java.util.List;

public class GpsTrack {
    private List<GpsTrackPoint> points;

    public GpsTrack(List<GpsTrackPoint> points) {
        this.points = points;
    }

    public List<GpsTrackPoint> getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return "GpsTrack{" +
                "points=" + points +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GpsTrack track = (GpsTrack) o;

        if (!points.equals(track.points)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return points.hashCode();
    }
}
