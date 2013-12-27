package lt.overdrive.trackparser.parsing;

import lt.overdrive.trackparser.domain.GpsTrail;
import org.junit.Test;

import java.io.File;

import static lt.overdrive.trackparser.utils.ResourceUtils.getFile;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;

public class ParserTest {
    @Test
    public void shouldDetectTcxFile_givenValidTcxFile() throws Exception {
        File tcxFile = getFile("tcx/test_with_ele.tcx");

        GpsTrail trail = Parser.parseFile(tcxFile);

        assertThat(trail.getTracks(), not(empty()));
    }

    @Test(expected = UnrecognizedFileException.class)
    public void shouldThrowInvalidFileException_givenInvalidFile() throws Exception {
        File tcxFile = getFile("tcx/invalid.tcx");

        GpsTrail trail = Parser.parseFile(tcxFile);
    }

    @Test
    public void shouldDetectGpxFile_givenValidGpxFile() throws Exception {
        File gpxFile = getFile("gpx/test_with_ele.gpx");

        GpsTrail trail = Parser.parseFile(gpxFile);

        assertThat(trail.getTracks(), not(empty()));
    }
}
