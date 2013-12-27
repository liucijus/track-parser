package lt.overdrive.trackparser;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import lt.overdrive.trackparser.domain.GpsTrack;
import lt.overdrive.trackparser.domain.GpsTrackPoint;
import lt.overdrive.trackparser.domain.GpsTrail;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.List;

public class CommonGpsTestDataHelper {
    public static final DateTimeZone TIME_ZONE = DateTimeZone.forOffsetMillis(0);
    public static final GpsTrackPoint POINT_1 =
            new GpsTrackPoint(54.709699, 25.245331, 165.282010, new DateTime(2012, 11, 24, 6, 42, 35, 0, TIME_ZONE));
    public static final GpsTrackPoint POINT_2 =
            new GpsTrackPoint(54.709651, 25.245321, 165.064160, new DateTime(2012, 11, 24, 6, 42, 36, 0, TIME_ZONE));
    public static final GpsTrackPoint POINT_3 =
            new GpsTrackPoint(54.709612, 25.245299, 164.895780, new DateTime(2012, 11, 24, 6, 42, 37, 0, TIME_ZONE));
    public static final GpsTrackPoint POINT_4 =
            new GpsTrackPoint(54.709577, 25.245242, 164.769670, new DateTime(2012, 11, 24, 6, 42, 38, 0, TIME_ZONE));
    public static final GpsTrackPoint POINT_5 =
            new GpsTrackPoint(54.709552, 25.245176, 164.748660, new DateTime(2012, 11, 24, 6, 42, 39, 0, TIME_ZONE));
    public static final GpsTrackPoint POINT_6 =
            new GpsTrackPoint(54.709523, 25.245106, 164.775070, new DateTime(2012, 11, 24, 6, 42, 40, 0, TIME_ZONE));

    public static GpsTrail prepareTrail(List<GpsTrackPoint> tracksPoints) {
        GpsTrack track = new GpsTrack(tracksPoints);
        return new GpsTrail(ImmutableList.of(track));
    }

    public static GpsTrail prepareTrailWithoutAltitude(List<GpsTrackPoint> tracksPoints) {
        return prepareTrail(prepareTrackPointsWithoutAltitude(tracksPoints));
    }

    public static List<GpsTrackPoint> prepareTrackPointsWithoutAltitude(List<GpsTrackPoint> tracksPoints) {
        return Lists.transform(tracksPoints, new Function<GpsTrackPoint, GpsTrackPoint>() {
            @Override
            public GpsTrackPoint apply(GpsTrackPoint input) {
                return new GpsTrackPoint(input.getLatitude(), input.getLongitude(), null, input.getTime());
            }
        });
    }

    public static GpsTrail prepareTrailWithoutTime(List<GpsTrackPoint> tracksPoints) {
        return prepareTrail(prepareTrackPointsWithoutTime(tracksPoints));
    }

    public static List<GpsTrackPoint> prepareTrackPointsWithoutTime(List<GpsTrackPoint> tracksPoints) {
        return Lists.transform(tracksPoints, new Function<GpsTrackPoint, GpsTrackPoint>() {
            @Override
            public GpsTrackPoint apply(GpsTrackPoint input) {
                return new GpsTrackPoint(input.getLatitude(), input.getLongitude(), input.getAltitude(), null);
            }
        });
    }
}
