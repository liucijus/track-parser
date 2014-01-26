package lt.overdrive.trackparser.processing;

import com.google.common.collect.ImmutableList;
import lt.overdrive.trackparser.domain.Track;
import lt.overdrive.trackparser.domain.TrackPoint;
import org.junit.Test;

import java.util.List;

import static lt.overdrive.trackparser.CommonGpsTestDataHelper.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TrackRectangleTest {
    @Test
    public void trackRectangleShouldBeNull_givenEmptyTrack() {
        Track track = trackOf();

        TrackRectangle rectangle = new TrackProcessor(track).calculateRectangle();

        assertThat(rectangle, is(nullValue()));
    }

    @Test
    public void trackRectangleCoordsShouldBeEqualTrackPoint_givenEmptyTrack() {
        Track track = trackOf(POINT_1);

        TrackRectangle rectangle = new TrackProcessor(track).calculateRectangle();

        assertThat(rectangle.getTopRightPoint(), is(notNullValue()));
        assertThat(rectangle.getTopRightPoint(), is(rectangle.getBottomLeftPoint()));
    }

    @Test
    public void trackRectangleCoordsShouldBeCorrect_givenCorrectTrack() {
        Track track = trackOf(POINT_1, POINT_2, POINT_3);
        TrackPoint topRightPoint = new TrackPoint(POINT_1.getLatitude(), POINT_1.getLongitude());
        TrackPoint bottomLeftPoint = new TrackPoint(POINT_3.getLatitude(), POINT_3.getLongitude());

        TrackRectangle rectangle = new TrackProcessor(track).calculateRectangle();

        assertThat(rectangle.getTopRightPoint(), equalTo(topRightPoint));
        assertThat(rectangle.getBottomLeftPoint(), equalTo(bottomLeftPoint));
    }

    @Test
    public void trackCenterCoordsShouldBeCorrect_givenCorrectTrack() {
        Track track = trackOf(POINT_1, POINT_2, POINT_3);

        TrackRectangle rectangle = new TrackProcessor(track).calculateRectangle();

        TrackPoint centerPoint = new TrackPoint(54.7096555, 25.245314999999998);
        assertThat(rectangle.getCenterPoint(), equalTo(centerPoint));
    }

    @Test
    public void trailRectangleShouldBeCorrect_givenCorrectTrail() {
        List<Track> tracks = ImmutableList.of(
                trackOf(POINT_1, POINT_2, POINT_3),
                trackOf(POINT_4, POINT_5, POINT_6)
        );
        TrackPoint topRightPoint = new TrackPoint(POINT_1.getLatitude(), POINT_1.getLongitude());
        TrackPoint bottomLeftPoint = new TrackPoint(POINT_6.getLatitude(), POINT_6.getLongitude());

        TrackRectangle rectangle = TrackProcessor.calculateRectangle(tracks);

        assertThat(rectangle.getTopRightPoint(), equalTo(topRightPoint));
        assertThat(rectangle.getBottomLeftPoint(), equalTo(bottomLeftPoint));
    }
}
