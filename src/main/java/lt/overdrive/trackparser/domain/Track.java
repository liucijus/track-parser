package lt.overdrive.trackparser.domain;

import java.util.List;

public class Track {
    private List<TrackPoint> points;

    public Track(List<TrackPoint> points) {
        this.points = points;
    }

    public List<TrackPoint> getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return "Track{" +
                "points=" + points +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Track track = (Track) o;

        if (!points.equals(track.points)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return points.hashCode();
    }
}
