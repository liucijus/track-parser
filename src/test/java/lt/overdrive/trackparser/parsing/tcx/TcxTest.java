package lt.overdrive.trackparser.parsing.tcx;

import com.google.common.collect.ImmutableList;
import lt.overdrive.trackparser.parsing.ParserException;
import lt.overdrive.trackparser.domain.GpsTrail;
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
        GpsTrail trail = new TcxParser().parse(getFile("tcx/valid.tcx"));

        assertThat(trail.getTracks().size(), equalTo(1));
    }

    @Test
    public void parserShouldLoadTwoTracks_givenTwoTracksFile() throws Exception {
        GpsTrail trail = new TcxParser().parse(getFile("tcx/2activities.tcx"));

        assertThat(trail.getTracks().size(), equalTo(2));
    }

    @Test
    public void parserShouldSkipTrackPointsWithoutPosition() throws Exception {
        String fileName = "tcx/missing_position.tcx";
        GpsTrail expectedTrail = prepareTrail(ImmutableList.of(POINT_1, POINT_3));

        GpsTrail trail = new TcxParser().parse(getFile(fileName));

        assertThat(trail, Matchers.samePropertyValuesAs(expectedTrail));
    }

    @Test
    public void parserShouldLoadDomainDataCorrectly_givenTcxFileWithAltitude() throws Exception {
        String fileName = "tcx/test_with_ele.tcx";
        GpsTrail expected = prepareTrail(ImmutableList.of(POINT_1, POINT_2, POINT_3, POINT_4, POINT_5, POINT_6));

        GpsTrail trail = new TcxParser().parse(getFile(fileName));

        assertThat(trail, Matchers.samePropertyValuesAs(expected));
    }

    @Test
    public void parserShouldLoadDomainDataCorrectly_givenTcxFileWithoutAltitude() throws Exception {
        String fileName = "tcx/test_no_ele.tcx";
        GpsTrail expected = prepareTrailWithoutAltitude(ImmutableList.of(POINT_1, POINT_2, POINT_3, POINT_4, POINT_5, POINT_6));

        GpsTrail trail = new TcxParser().parse(getFile(fileName));

        assertThat(trail, Matchers.samePropertyValuesAs(expected));
    }
}

