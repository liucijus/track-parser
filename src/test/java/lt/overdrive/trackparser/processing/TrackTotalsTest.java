package lt.overdrive.trackparser.processing;

import com.google.common.collect.ImmutableList;
import lt.overdrive.trackparser.domain.GpsTrack;
import org.joda.time.Seconds;
import org.junit.Test;

import java.util.Collections;

import static lt.overdrive.trackparser.CommonGpsTestDataHelper.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.IsCloseTo.closeTo;

public class TrackTotalsTest {

    @Test
    public void totalDistanceShouldBeZero_givenEmptyTrack() {
        GpsTrack track = new GpsTrack(Collections.EMPTY_LIST);

        TrackTotals totals = new TrackProcessor(track).calculateTotals();

        assertThat(totals.getDistance(), closeTo(0, 0.005));
    }

    @Test
    public void totalDistanceShouldBeZero_givenOnePointTrack() {
        GpsTrack track = new GpsTrack(ImmutableList.of(POINT_1));

        TrackTotals totals = new TrackProcessor(track).calculateTotals();

        assertThat(totals.getDistance(), closeTo(0, 0.005));
    }

    @Test
    public void totalDistanceShouldBeCorrect_givenTrackWithTrackPoints() {
        GpsTrack track = new GpsTrack(ImmutableList.of(POINT_1, POINT_2, POINT_3));

        TrackTotals totals = new TrackProcessor(track).calculateTotals();

        assertThat(totals.getDistance(), closeTo(9.94, 0.005));
    }

    @Test
    public void totalTimeShouldBeZero_givenEmptyTrack() {
        GpsTrack track = new GpsTrack(Collections.EMPTY_LIST);

        TrackTotals totals = new TrackProcessor(track).calculateTotals();

        assertThat(totals.getTime(), equalTo(Seconds.seconds(0)));
    }

    @Test
    public void totalTimeShouldBeZero_givenOnePointTrack() {
        GpsTrack track = new GpsTrack(ImmutableList.of(POINT_1));

        TrackTotals totals = new TrackProcessor(track).calculateTotals();

        assertThat(totals.getTime(), equalTo(Seconds.seconds(0)));
    }

    @Test
    public void totalTimeShouldBeCorrect_givenTrackWithPoints() {
        GpsTrack track = new GpsTrack(ImmutableList.of(POINT_1, POINT_2, POINT_3));

        TrackTotals totals = new TrackProcessor(track).calculateTotals();

        assertThat(totals.getTime(), equalTo(Seconds.seconds(2)));
    }

    @Test
    public void totalTimeShouldNotBeSet_givenTimelessTrack() {
        GpsTrack track = new GpsTrack(prepareTrackPointsWithoutTime(ImmutableList.of(POINT_1, POINT_2, POINT_3)));

        TrackTotals totals = new TrackProcessor(track).calculateTotals();

        assertThat(totals.getTime(), is(nullValue()));
    }

    @Test
    public void averageSpeedShouldBeCorrect_givenTrackWithPoints() {
        GpsTrack track = new GpsTrack(ImmutableList.of(POINT_1, POINT_2, POINT_3));

        TrackTotals totals = new TrackProcessor(track).calculateTotals();

        assertThat(totals.getAverageSpeedInMetersPerSecond(), closeTo(4.97, 0.005));
    }

    @Test
    public void averageSpeedShouldNotBeSet_giventTimelessTrack() {
        GpsTrack track = new GpsTrack(prepareTrackPointsWithoutTime(ImmutableList.of(POINT_1, POINT_2, POINT_3)));

        TrackTotals totals = new TrackProcessor(track).calculateTotals();

        assertThat(totals.getAverageSpeedInMetersPerSecond(), is(nullValue()));
    }

    @Test
    public void totalAscentShouldBeCorrect_givenTrackWithPoints() {
        GpsTrack track = new GpsTrack(ImmutableList.of(POINT_1, POINT_2, POINT_3));

        TrackTotals totals = new TrackProcessor(track).calculateTotals();

        assertThat(totals.getAscent(), closeTo(0, 0.005));
    }

    @Test
    public void totalAscentShouldNotBeSet_givenTrackWithoutAltitude() {
        GpsTrack track = new GpsTrack(prepareTrackPointsWithoutAltitude(ImmutableList.of(POINT_1, POINT_2, POINT_3)));

        TrackTotals totals = new TrackProcessor(track).calculateTotals();

        assertThat(totals.getAscent(), is(nullValue()));
    }

    @Test
    public void totalDescentShouldBeCorrect_givenTrackWithPoints() {
        GpsTrack track = new GpsTrack(ImmutableList.of(POINT_1, POINT_2, POINT_3));

        TrackTotals totals = new TrackProcessor(track).calculateTotals();

        assertThat(totals.getDescent(), closeTo(0.39, 0.005));
    }

    @Test
    public void totalDescentShouldNotBeSet_givenTrackWithoutAltitude() {
        GpsTrack track = new GpsTrack(prepareTrackPointsWithoutAltitude(ImmutableList.of(POINT_1, POINT_2, POINT_3)));

        TrackTotals totals = new TrackProcessor(track).calculateTotals();

        assertThat(totals.getDescent(), is(nullValue()));
    }

    @Test
    public void totalAscentShouldBeZero_givenOnePointTrack() {
        GpsTrack track = new GpsTrack(ImmutableList.of(POINT_1));

        TrackTotals totals = new TrackProcessor(track).calculateTotals();

        assertThat(totals.getAscent(), closeTo(0, 0.005));
    }

    @Test
    public void totalDescentShouldBeZero_givenOnePointTrack() {
        GpsTrack track = new GpsTrack(ImmutableList.of(POINT_1));

        TrackTotals totals = new TrackProcessor(track).calculateTotals();

        assertThat(totals.getDescent(), closeTo(0, 0.005));
    }

    @Test
    public void maxSpeedShouldBeZero_givenEmptyTrack() {
        GpsTrack track = new GpsTrack(Collections.EMPTY_LIST);

        TrackTotals totals = new TrackProcessor(track).calculateTotals();

        assertThat(totals.getMaxSpeed(), closeTo(0, 0.005));
    }

    @Test
    public void maxSpeedShouldBeZero_givenOnePointTrack() {
        GpsTrack track = new GpsTrack(ImmutableList.of(POINT_1));

        TrackTotals totals = new TrackProcessor(track).calculateTotals();

        assertThat(totals.getMaxSpeed(), closeTo(0, 0.005));
    }

    @Test
    public void maxSpeedShouldBeCorrect_givenTrackWithTime() {
        GpsTrack track = new GpsTrack(ImmutableList.of(POINT_1, POINT_2, POINT_3));

        TrackTotals totals = new TrackProcessor(track).calculateTotals();

        assertThat(totals.getMaxSpeed(), closeTo(5.38, 0.005));
    }

    @Test
    public void minSpeedShouldBeZero_givenEmptyTrack() {
        GpsTrack track = new GpsTrack(Collections.EMPTY_LIST);

        TrackTotals totals = new TrackProcessor(track).calculateTotals();

        assertThat(totals.getMinSpeed(), closeTo(0, 0.005));
    }

    @Test
    public void minSpeedShouldBeZero_givenOnePointTrack() {
        GpsTrack track = new GpsTrack(ImmutableList.of(POINT_1));

        TrackTotals totals = new TrackProcessor(track).calculateTotals();

        assertThat(totals.getMinSpeed(), closeTo(0, 0.005));
    }

    @Test
    public void minSpeedShouldBeCorrect_givenTrackWithTime() {
        GpsTrack track = new GpsTrack(ImmutableList.of(POINT_1, POINT_2, POINT_3));

        TrackTotals totals = new TrackProcessor(track).calculateTotals();

        assertThat(totals.getMinSpeed(), closeTo(4.56, 0.005));
    }

    @Test
    public void maxSpeedShouldNotBeSet_givenTrackWithoutTime() {
        GpsTrack track = new GpsTrack(prepareTrackPointsWithoutTime(ImmutableList.of(POINT_1, POINT_2, POINT_3)));

        TrackTotals totals = new TrackProcessor(track).calculateTotals();

        assertThat(totals.getMaxSpeed(), is(nullValue()));
    }

    @Test
    public void minSpeedShouldNotBeSet_givenTrackWithoutTime() {
        GpsTrack track = new GpsTrack(prepareTrackPointsWithoutTime(ImmutableList.of(POINT_1, POINT_2, POINT_3)));

        TrackTotals totals = new TrackProcessor(track).calculateTotals();

        assertThat(totals.getMinSpeed(), is(nullValue()));
    }
}
