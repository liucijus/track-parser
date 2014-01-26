package lt.overdrive.trackparser.parsing.gpx;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import lt.overdrive.trackparser.domain.TrackPoint;
import lt.overdrive.trackparser.domain.Trail;
import lt.overdrive.trackparser.parsing.AbstractParser;
import lt.overdrive.trackparser.parsing.ParserException;
import lt.overdrive.trackparser.domain.Track;
import lt.overdrive.trackparser.parsing.gpx.schema.GpxType;
import lt.overdrive.trackparser.parsing.gpx.schema.TrkType;
import lt.overdrive.trackparser.parsing.gpx.schema.TrksegType;
import lt.overdrive.trackparser.parsing.gpx.schema.WptType;

import javax.xml.bind.UnmarshalException;
import javax.xml.validation.Schema;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static lt.overdrive.trackparser.utils.ResourceUtils.loadSchema;

public class GpxParser extends AbstractParser {
    @Override
    protected Trail loadTrail(File file) throws Exception {
        GpxType gpx = (GpxType) loadXml(file, GpxType.class);
        return extractTrailFromTcx(gpx);
    }

    private Trail extractTrailFromTcx(GpxType gpx) {
        List<Track> tracks = new ArrayList<>();
        for (TrkType trk : gpx.getTrk()) {
            for (TrksegType seg : trk.getTrkseg()) {
                List<TrackPoint> points = Lists.transform(seg.getTrkpt(), new Function<WptType, TrackPoint>() {
                    @Override
                    public TrackPoint apply(WptType input) {
                        return convert2GpsTrackPoint(input);
                    }
                });
                tracks.add(new Track(points));
            }
        }
        return new Trail(tracks);
    }

    private TrackPoint convert2GpsTrackPoint(WptType input) {
        BigDecimal elevation = input.getEle();
        return new TrackPoint(
                input.getLat().doubleValue(),
                input.getLon().doubleValue(),
                elevation != null ? elevation.doubleValue() : null,
                convertTime(input.getTime()));
    }

    @Override
    public Schema getSchema() throws ParserException {
        return loadSchema("gpx/gpx.xsd");
    }

    @Override
    protected Trail throwInvalidFileException(UnmarshalException pe) throws ParserException {
        throw new InvalidGpxFile("Invalid gpx file.", pe);
    }
}
