package lt.overdrive.trackparser;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import lt.overdrive.trackparser.domain.Track;
import lt.overdrive.trackparser.domain.TrackPoint;
import lt.overdrive.trackparser.domain.Trail;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.Arrays;
import java.util.List;

public class CommonGpsTestDataHelper {
    public static final DateTimeZone TIME_ZONE = DateTimeZone.forOffsetMillis(0);
    public static final TrackPoint POINT_1 =
            new TrackPoint(54.709699, 25.245331, 165.282010, new DateTime(2012, 11, 24, 6, 42, 35, 0, TIME_ZONE));
    public static final TrackPoint POINT_2 =
            new TrackPoint(54.709651, 25.245321, 165.064160, new DateTime(2012, 11, 24, 6, 42, 36, 0, TIME_ZONE));
    public static final TrackPoint POINT_3 =
            new TrackPoint(54.709612, 25.245299, 164.895780, new DateTime(2012, 11, 24, 6, 42, 37, 0, TIME_ZONE));
    public static final TrackPoint POINT_4 =
            new TrackPoint(54.709577, 25.245242, 164.769670, new DateTime(2012, 11, 24, 6, 42, 38, 0, TIME_ZONE));
    public static final TrackPoint POINT_5 =
            new TrackPoint(54.709552, 25.245176, 164.748660, new DateTime(2012, 11, 24, 6, 42, 39, 0, TIME_ZONE));
    public static final TrackPoint POINT_6 =
            new TrackPoint(54.709523, 25.245106, 164.775070, new DateTime(2012, 11, 24, 6, 42, 40, 0, TIME_ZONE));

    public static Trail prepareTrail(TrackPoint... points) {
        Track track = trackOf(points);
        return new Trail(ImmutableList.of(track));
    }

    public static Trail prepareTrailWithoutAltitude(TrackPoint... points) {
        return prepareTrail(prepareTrackPointsWithoutAltitude(points));
    }

    public static TrackPoint[] prepareTrackPointsWithoutAltitude(TrackPoint... points) {
        List<TrackPoint> withoutAltitude = Lists.transform(Arrays.asList(points), new Function<TrackPoint, TrackPoint>() {
            @Override
            public TrackPoint apply(TrackPoint input) {
                return new TrackPoint(input.getLatitude(), input.getLongitude(), null, input.getTime());
            }
        });
        return withoutAltitude.toArray(new TrackPoint[withoutAltitude.size()]);
    }

    public static Trail prepareTrailWithoutTime(TrackPoint... tracksPoints) {
        return prepareTrail(prepareTrackPointsWithoutTime(tracksPoints));
    }

    public static TrackPoint[] prepareTrackPointsWithoutTime(TrackPoint... points) {
        List<TrackPoint> withoutTime = Lists.transform(Arrays.asList(points), new Function<TrackPoint, TrackPoint>() {
            @Override
            public TrackPoint apply(TrackPoint input) {
                return new TrackPoint(input.getLatitude(), input.getLongitude(), input.getAltitude(), null);
            }
        });
        return withoutTime.toArray(new TrackPoint[withoutTime.size()]);
    }

    public static Track trackOf(TrackPoint... points) {
        return new Track(Arrays.asList(points));
    }

    public static Track trackWithoutTimeOf(TrackPoint... points) {
        return trackOf(prepareTrackPointsWithoutTime(points));
    }

    public static Track trackWithoutAltitudeOf(TrackPoint... points) {
        return trackOf(prepareTrackPointsWithoutAltitude(points));
    }
}
