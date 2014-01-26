package lt.overdrive.trackparser.parsing.tcx;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import lt.overdrive.trackparser.domain.TrackPoint;
import lt.overdrive.trackparser.domain.Trail;
import lt.overdrive.trackparser.parsing.AbstractParser;
import lt.overdrive.trackparser.parsing.ParserException;
import lt.overdrive.trackparser.domain.Track;
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
    protected Trail loadTrail(File file) throws Exception {
        TrainingCenterDatabaseT database = (TrainingCenterDatabaseT) loadXml(file, TrainingCenterDatabaseT.class);
        return new Trail(extractTracks(database));
    }

    private List<Track> extractTracks(TrainingCenterDatabaseT database) {
        List<Track> tracks = new ArrayList<>();
        for (ActivityT activityT : database.getActivities().getActivity()) {
            extractActivityTracks(tracks, activityT);
        }
        return tracks;
    }

    private void extractActivityTracks(List<Track> tracks, ActivityT activityT) {
        for (ActivityLapT lap : activityT.getLap()) {
            extractLapTracks(tracks, lap);
        }
    }

    private void extractLapTracks(List<Track> tracks, ActivityLapT lap) {
        for (TrackT trackT : lap.getTrack()) {
            tracks.add(new Track(extractGpsTrackPoints(trackT)));
        }
    }

    private List<TrackPoint> extractGpsTrackPoints(TrackT trackT) {
        return new ArrayList<>(Collections2.transform(
                filterPointsWithoutPosition(trackT.getTrackpoint()),
                new Function<TrackpointT, TrackPoint>() {
                    @Override
                    public TrackPoint apply(TrackpointT point) {
                        return convert2GpsTrackPoint(point);
                    }
                }));
    }

    private TrackPoint convert2GpsTrackPoint(TrackpointT point) {
        PositionT position = point.getPosition();
        Double altitudeMeters = point.getAltitudeMeters();
        return new TrackPoint(
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
    protected Trail throwInvalidFileException(UnmarshalException pe) throws ParserException {
        throw new InvalidTxcFile("Invalid tcx file.", pe);
    }
}
