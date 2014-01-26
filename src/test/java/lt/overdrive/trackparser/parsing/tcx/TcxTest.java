package lt.overdrive.trackparser.parsing.tcx;

import lt.overdrive.trackparser.domain.Trail;
import lt.overdrive.trackparser.parsing.ParserException;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;

import static lt.overdrive.trackparser.CommonGpsTestDataHelper.*;
import static lt.overdrive.trackparser.utils.ResourceUtils.getFile;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TcxTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test(expected = InvalidTxcFile.class)
    public void parserShouldThrowException_givenInvalidTcxFile() throws Exception {
        File file = getFile("tcx/invalid.tcx");

        new TcxParser().parse(file);
    }

    @Test
    public void parserShouldThrowException_givenNonExistingFile() throws Exception {
        File file = new File("/non-existing-file");
        exception.expect(ParserException.class);
        exception.expectMessage("File " + file + " does not exist.");

        new TcxParser().parse(file);
    }

    @Test
    public void parserShouldLoadOneActivity_givenOneActivityFile() throws Exception {
        Trail trail = new TcxParser().parse(getFile("tcx/valid.tcx"));

        assertThat(trail.getTracks().size(), equalTo(1));
    }

    @Test
    public void parserShouldLoadTwoTracks_givenTwoTracksFile() throws Exception {
        Trail trail = new TcxParser().parse(getFile("tcx/2activities.tcx"));

        assertThat(trail.getTracks().size(), equalTo(2));
    }

    @Test
    public void parserShouldSkipTrackPointsWithoutPosition() throws Exception {
        Trail expectedTrail = prepareTrail(POINT_1, POINT_3);

        Trail trail = new TcxParser().parse(getFile("tcx/missing_position.tcx"));

        assertThat(trail, Matchers.samePropertyValuesAs(expectedTrail));
    }

    @Test
    public void parserShouldLoadDomainDataCorrectly_givenTcxFileWithAltitude() throws Exception {
        Trail expected = prepareTrail(POINT_1, POINT_2, POINT_3, POINT_4, POINT_5, POINT_6);

        Trail trail = new TcxParser().parse(getFile("tcx/test_with_ele.tcx"));

        assertThat(trail, Matchers.samePropertyValuesAs(expected));
    }

    @Test
    public void parserShouldLoadDomainDataCorrectly_givenTcxFileWithoutAltitude() throws Exception {
        Trail expected = prepareTrailWithoutAltitude(POINT_1, POINT_2, POINT_3, POINT_4, POINT_5, POINT_6);

        Trail trail = new TcxParser().parse(getFile("tcx/test_no_ele.tcx"));

        assertThat(trail, Matchers.samePropertyValuesAs(expected));
    }
}

