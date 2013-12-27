package lt.overdrive.trackparser.parsing.tcx;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import lt.overdrive.trackparser.parsing.AbstractParser;
import lt.overdrive.trackparser.parsing.ParserException;
import lt.overdrive.trackparser.domain.GpsTrack;
import lt.overdrive.trackparser.domain.GpsTrackPoint;
import lt.overdrive.trackparser.domain.GpsTrail;
import lt.overdrive.trackparser.parsing.tcx.schema.ActivityLapT;
import lt.overdrive.trackparser.parsing.tcx.schema.ActivityT;
import lt.overdrive.trackparser.parsing.tcx.schema.PositionT;
import lt.overdrive.trackparser.parsing.tcx.schema.TrackT;
import lt.overdrive.trackparser.parsing.tcx.schema.TrackpointT;
import lt.overdrive.trackparser.parsing.tcx.schema.TrainingCenterDatabaseT;

import javax.xml.bind.UnmarshalException;
import javax.xml.validation.Schema;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static lt.overdrive.trackparser.utils.ResourceUtils.loadSchema;

public class TcxParser extends AbstractParser {
    @Override
    protected GpsTrail loadTrail(File file) throws Exception {
        TrainingCenterDatabaseT database = (TrainingCenterDatabaseT) loadXml(file, TrainingCenterDatabaseT.class);
        return new GpsTrail(extractTracks(database));
    }

    private List<GpsTrack> extractTracks(TrainingCenterDatabaseT database) {
        List<GpsTrack> tracks = new ArrayList<>();
        for (ActivityT activityT : database.getActivities().getActivity()) {
            extractActivityTracks(tracks, activityT);
        }
        return tracks;
    }

    private void extractActivityTracks(List<GpsTrack> tracks, ActivityT activityT) {
        for (ActivityLapT lap : activityT.getLap()) {
            extractLapTracks(tracks, lap);
        }
    }

    private void extractLapTracks(List<GpsTrack> tracks, ActivityLapT lap) {
        for (TrackT trackT : lap.getTrack()) {
            tracks.add(new GpsTrack(extractGpsTrackPoints(trackT)));
        }
    }

    private List<GpsTrackPoint> extractGpsTrackPoints(TrackT trackT) {
        return new ArrayList<>(Collections2.transform(
                filterPointsWithoutPosition(trackT.getTrackpoint()),
                new Function<TrackpointT, GpsTrackPoint>() {
                    @Override
                    public GpsTrackPoint apply(TrackpointT point) {
                        return convert2GpsTrackPoint(point);
                    }
                }));
    }

    private GpsTrackPoint convert2GpsTrackPoint(TrackpointT point) {
        PositionT position = point.getPosition();
        Double altitudeMeters = point.getAltitudeMeters();
        return new GpsTrackPoint(
                position.getLatitudeDegrees(),
                position.getLongitudeDegrees(),
                altitudeMeters != null ? altitudeMeters : null,
                convertTime(point.getTime()));
    }

    private Collection<TrackpointT> filterPointsWithoutPosition(List<TrackpointT> points) {
        return Collections2.filter(points,
                new Predicate<TrackpointT>() {
                    @Override
                    public boolean apply(TrackpointT input) {
                        return input.getPosition() != null;
                    }
                });
    }

    public Schema getSchema() throws ParserException {
        return loadSchema("tcx/TrainingCenterDatabasev2.xsd");
    }

    @Override
    protected GpsTrail throwInvalidFileException(UnmarshalException pe) throws ParserException {
        throw new InvalidTxcFile("Invalid tcx file.", pe);
    }
}
