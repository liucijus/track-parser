package lt.overdrive.trackparser.processing;

import com.google.common.collect.ImmutableList;
import lt.overdrive.trackparser.domain.GpsTrack;
import lt.overdrive.trackparser.domain.GpsTrackPoint;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static lt.overdrive.trackparser.CommonGpsTestDataHelper.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TrackRectangleTest {
    @Test
    public void trackRectangleShouldBeNull_givenEmptyTrack() {
        GpsTrack track = new GpsTrack(Collections.EMPTY_LIST);

        TrackRectangle rectangle = new TrackProcessor(track).calculateRectangle();

        assertThat(rectangle, is(nullValue()));
    }

    @Test
    public void trackRectangleCoordsShouldBeEqualTrackPoint_givenEmptyTrack() {
        GpsTrack track = new GpsTrack(ImmutableList.of(POINT_1));

        TrackRectangle rectangle = new TrackProcessor(track).calculateRectangle();

        assertThat(rectangle.getTopRightPoint(), is(notNullValue()));
        assertThat(rectangle.getTopRightPoint(), is(rectangle.getBottomLeftPoint()));
    }

    @Test
    public void trackRectangleCoordsShouldBeCorrect_givenCorrectTrack() {
        GpsTrack track = new GpsTrack(ImmutableList.of(POINT_1, POINT_2, POINT_3));

        TrackRectangle rectangle = new TrackProcessor(track).calculateRectangle();

        GpsTrackPoint topLeftPoint = new GpsTrackPoint(POINT_1.getLatitude(), POINT_1.getLongitude());
        GpsTrackPoint bottomRightPoint = new GpsTrackPoint(POINT_3.getLatitude(), POINT_3.getLongitude());
        assertThat(rectangle.getTopRightPoint(), equalTo(topLeftPoint));
        assertThat(rectangle.getBottomLeftPoint(), equalTo(bottomRightPoint));
    }

    @Test
    public void trackCenterCoordsShouldBeCorrect_givenCorrectTrack() {
        GpsTrack track = new GpsTrack(ImmutableList.of(POINT_1, POINT_2, POINT_3));

        TrackRectangle rectangle = new TrackProcessor(track).calculateRectangle();

        GpsTrackPoint centerPoint = new GpsTrackPoint(54.7096555, 25.245314999999998);
        assertThat(rectangle.getCenterPoint(), equalTo(centerPoint));
    }

    @Test
    public void trailRectableShouldBeCorrect_givenCorrectTrail() {
        List<GpsTrack> tracks = ImmutableList.of(
                new GpsTrack(ImmutableList.of(POINT_1, POINT_2, POINT_3)),
                new GpsTrack(ImmutableList.of(POINT_4, POINT_5, POINT_6))
        );

        TrackRectangle rectangle = TrackProcessor.calculateRectangle(tracks);

        GpsTrackPoint topLeftPoint = new GpsTrackPoint(POINT_1.getLatitude(), POINT_1.getLongitude());
        GpsTrackPoint bottomRightPoint = new GpsTrackPoint(POINT_6.getLatitude(), POINT_6.getLongitude());
        assertThat(rectangle.getTopRightPoint(), equalTo(topLeftPoint));
        assertThat(rectangle.getBottomLeftPoint(), equalTo(bottomRightPoint));
    }
}
