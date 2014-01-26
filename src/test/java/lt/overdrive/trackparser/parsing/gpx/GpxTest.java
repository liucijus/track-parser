package lt.overdrive.trackparser.parsing.gpx;

import lt.overdrive.trackparser.domain.Trail;
import lt.overdrive.trackparser.parsing.ParserException;
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
    public void parserShouldLoadOneTrack_givenOneTrackFile() throws Exception {
        Trail trail = new GpxParser().parse(getFile("gpx/test_with_ele.gpx"));

        assertThat(trail.getTracks().size(), equalTo(1));
    }

    @Test
    public void parserShouldLoadTwoTracks_givenTwoTracksFile() throws Exception {
        Trail trail = new GpxParser().parse(getFile("gpx/2tracks.gpx"));

        assertThat(trail.getTracks().size(), equalTo(2));
    }

    @Test
    public void parserShouldLoadDomainDataCorrectly_givenCorrectGpxFile() throws Exception {
        Trail expected = prepareTrail(POINT_1, POINT_2, POINT_3, POINT_4, POINT_5, POINT_6);

        Trail trail = new GpxParser().parse(getFile("gpx/test_with_ele.gpx"));

        assertThat(trail, samePropertyValuesAs(expected));
    }

    @Test
    public void parserShouldLoadDomainDataCorrectly_givenGpxFileWithoutAltitude() throws Exception {
        Trail expected = prepareTrailWithoutAltitude(POINT_1, POINT_2, POINT_3, POINT_4, POINT_5, POINT_6);

        Trail trail = new GpxParser().parse(getFile("gpx/test_no_ele.gpx"));

        assertThat(trail, samePropertyValuesAs(expected));
    }

    @Test
    public void parserShouldLoadDomainDataCorrectly_givenGpxFileWithoutTime() throws Exception {
        Trail expected = prepareTrailWithoutTime(POINT_1, POINT_2, POINT_3, POINT_4, POINT_5, POINT_6);

        Trail trail = new GpxParser().parse(getFile("gpx/test_no_time.gpx"));

        assertThat(trail, samePropertyValuesAs(expected));
    }
}
