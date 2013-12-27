package lt.overdrive.trackparser.domain;

import java.util.List;

public class GpsTrail {
    private List<GpsTrack> tracks;

    public GpsTrail(List<GpsTrack> tracks) {
        this.tracks = tracks;
    }

    public List<GpsTrack> getTracks() {
        return tracks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GpsTrail gpsTrail = (GpsTrail) o;

        return tracks.equals(gpsTrail.tracks);
    }

    @Override
    public int hashCode() {
        return tracks.hashCode();
    }
}
