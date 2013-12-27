package lt.overdrive.trackparser.parsing.gpx;

import com.google.common.collect.ImmutableList;
import lt.overdrive.trackparser.parsing.ParserException;
import lt.overdrive.trackparser.domain.GpsTrail;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;

import static lt.overdrive.trackparser.CommonGpsTestDataHelper.*;
import static lt.overdrive.trackparser.utils.ResourceUtils.getFile;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

public class GpxTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test(expected = InvalidGpxFile.class)
    public void parserShouldThrowException_givenInvalidGpxFile() throws Exception {
        File file = getFile("gpx/invalid.gpx");

        new GpxParser().parse(file);
    }

    @Test
    public void parserShouldThrowException_givenNonExistingFile() throws Exception {
        File file = new File("/non-existing-file");
        exception.expect(ParserException.class);
        exception.expectMessage("File " + file + " does not exist.");

        new GpxParser().parse(file);
    }

    @Test
    public void parserShouldLoadOneTrack_givenOneActivityFile() throws Exception {
        GpsTrail trail = new GpxParser().parse(getFile("gpx/test_with_ele.gpx"));

        assertThat(trail.getTracks().size(), equalTo(1));
    }

    @Test
    public void parserShouldLoadTwoTracks_givenTwoTracksFile() throws Exception {
        GpsTrail trail = new GpxParser().parse(getFile("gpx/2tracks.gpx"));

        assertThat(trail.getTracks().size(), equalTo(2));
    }

    @Test
    public void parserShouldLoadDomainDataCorrectly_givenCorrectGpxFile() throws Exception {
        String fileName = "gpx/test_with_ele.gpx";
        GpsTrail expected = prepareTrail(ImmutableList.of(POINT_1, POINT_2, POINT_3, POINT_4, POINT_5, POINT_6));

        GpsTrail trail = new GpxParser().parse(getFile(fileName));

        assertThat(trail, samePropertyValuesAs(expected));
    }

    @Test
    public void parserShouldLoadDomainDataCorrectly_givenGpxFileWithoutAltitude() throws Exception {
        String fileName = "gpx/test_no_ele.gpx";
        GpsTrail expected = prepareTrailWithoutAltitude(ImmutableList.of(POINT_1, POINT_2, POINT_3, POINT_4, POINT_5, POINT_6));

        GpsTrail trail = new GpxParser().parse(getFile(fileName));

        assertThat(trail, samePropertyValuesAs(expected));
    }

    @Test
    public void parserShouldLoadDomainDataCorrectly_givenGpxFileWithoutTime() throws Exception {
        String fileName = "gpx/test_no_time.gpx";
        GpsTrail expected = prepareTrailWithoutTime(ImmutableList.of(POINT_1, POINT_2, POINT_3, POINT_4, POINT_5, POINT_6));

        GpsTrail trail = new GpxParser().parse(getFile(fileName));

        assertThat(trail, samePropertyValuesAs(expected));
    }
}
