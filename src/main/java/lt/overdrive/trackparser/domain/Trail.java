package lt.overdrive.trackparser.domain;

import java.util.List;

public class Trail {
    private List<Track> tracks;

    public Trail(List<Track> tracks) {
        this.tracks = tracks;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trail trail = (Trail) o;

        return tracks.equals(trail.tracks);
    }

    @Override
    public int hashCode() {
        return tracks.hashCode();
    }
}
