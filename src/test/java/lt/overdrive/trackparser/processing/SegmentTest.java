package lt.overdrive.trackparser.processing;

import com.google.common.collect.ImmutableList;
import lt.overdrive.trackparser.domain.GpsTrack;
import lt.overdrive.trackparser.processing.domain.SegmentTrack;
import lt.overdrive.trackparser.processing.domain.Segment;
import org.junit.Test;

import java.util.Collections;

import static lt.overdrive.trackparser.CommonGpsTestDataHelper.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;

public class SegmentTest {
    @Test
    public void segmentsShouldBeEmpty_givenEmptyTrack() {
        GpsTrack track = new GpsTrack(Collections.EMPTY_LIST);

        SegmentTrack segmentTrack = new TrackProcessor(track).getSegmentTrack();

        assertThat(segmentTrack.getSegments(), is(empty()));
    }

    @Test
    public void segmentsShouldBeEmpty_givenOnePointTrack() {
        GpsTrack track = new GpsTrack(ImmutableList.of(POINT_1));

        SegmentTrack segmentTrack = new TrackProcessor(track).getSegmentTrack();

        assertThat(segmentTrack.getSegments(), is(empty()));
    }

    @Test
    public void shouldBeOnePoint_givenOnePointTrack() {
        GpsTrack track = new GpsTrack(ImmutableList.of(POINT_1));

        SegmentTrack segmentTrack = new TrackProcessor(track).getSegmentTrack();

        assertThat(segmentTrack.getSegmentPoints().size(), equalTo(1));
    }

    @Test
    public void thereShouldBeOneLessSegmentsThanPoints_givenCorrectTrack() {
        GpsTrack track = new GpsTrack(ImmutableList.of(POINT_1, POINT_2, POINT_3));

        SegmentTrack segmentTrack = new TrackProcessor(track).getSegmentTrack();

        assertThat(segmentTrack.getSegments().size(), equalTo(segmentTrack.getSegmentPoints().size() - 1));
    }

    @Test
    public void thereShouldBeCorrectNumberOfSegments_givenCorrectTrack() {
        GpsTrack track = new GpsTrack(ImmutableList.of(POINT_1, POINT_2, POINT_3));

        SegmentTrack segmentTrack = new TrackProcessor(track).getSegmentTrack();

        assertThat(segmentTrack.getSegments().size(), equalTo(2));
    }

    @Test
    public void segmentShouldHaveCorrectPoints_givenCorrectTrack() {
        GpsTrack track = new GpsTrack(ImmutableList.of(POINT_1, POINT_2));

        SegmentTrack segmentTrack = new TrackProcessor(track).getSegmentTrack();

        assertThat(segmentTrack.getSegments().get(0), equalTo(new Segment(POINT_1, POINT_2)));
    }

    @Test
    public void segmentTrackShouldHaveCorrectPoints_givenCorrectTrack() {
        GpsTrack track = new GpsTrack(ImmutableList.of(POINT_1, POINT_2));

        SegmentTrack segmentTrack = new TrackProcessor(track).getSegmentTrack();

        assertThat(segmentTrack.getSegmentPoints().get(0).getPoint(), equalTo(POINT_1));
        assertThat(segmentTrack.getSegmentPoints().get(1).getPoint(), equalTo(POINT_2));
    }

    //todo: finish implementing point/segment relations with acceleration
}
