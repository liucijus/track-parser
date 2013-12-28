package lt.overdrive.trackparser.domain;

import org.joda.time.DateTime;

public class GpsTrackPoint {
    private final Double latitude;
    private final Double longitude;
    private final Double altitude;
    private final DateTime time;

    /**
     * Defines track point
     *
     * @param latitude  - latitude in degrees
     * @param longitude - longitude in degrees
     * @param altitude  - altitude in meters
     * @param time      - time point is registered
     */
    public GpsTrackPoint(final Double latitude, final Double longitude, final Double altitude, final DateTime time) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;

        this.time = time;
    }

    public GpsTrackPoint(double maxLatitude, double minLongitude) {
        latitude = maxLatitude;
        longitude = minLongitude;
        this.altitude = null;

        this.time = null;
    }

    @Override
    public String toString() {
        return "GpsTrackPoint{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", altitude=" + altitude +
                ", time=" + time +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GpsTrackPoint that = (GpsTrackPoint) o;

        if (altitude != null ? !altitude.equals(that.altitude) : that.altitude != null) return false;
        if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null) return false;
        if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = latitude != null ? latitude.hashCode() : 0;
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (altitude != null ? altitude.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public DateTime getTime() {
        return time;
    }

    public Double getAltitude() {
        return altitude;
    }
}
