package lt.overdrive.trackparser.parsing;

import lt.overdrive.trackparser.domain.GpsTrail;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.File;

public abstract class AbstractParser implements GpsFileParser {
    public GpsTrail parse(File file) throws ParserException {
        if (!file.exists()) throw new ParserException("File " + file + " does not exist.");
        try {
            return loadTrail(file);
        } catch (UnmarshalException pe) {
            return throwInvalidFileException(pe);
        } catch (Exception e) {
            throw new ParserException("Parsing failed.", e);
        }
    }

    protected abstract GpsTrail throwInvalidFileException(UnmarshalException pe) throws ParserException;

    protected abstract GpsTrail loadTrail(File file) throws Exception;

    protected Object loadXml(File file, Class clazz) throws JAXBException, ParserException {
        JAXBContext context = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        unmarshaller.setSchema(getSchema());
        return ((JAXBElement) unmarshaller.unmarshal(file)).getValue();
    }

    protected DateTime convertTime(XMLGregorianCalendar id) {
        if (id == null) return null;
        else return new DateTime(
                getValueOrZeroIfUndefined(id.getYear()),
                getValueOrZeroIfUndefined(id.getMonth()),
                getValueOrZeroIfUndefined(id.getDay()),
                getValueOrZeroIfUndefined(id.getHour()),
                getValueOrZeroIfUndefined(id.getMinute()),
                getValueOrZeroIfUndefined(id.getSecond()),
                getValueOrZeroIfUndefined(id.getMillisecond()),
                DateTimeZone.forOffsetMillis(getValueOrZeroIfUndefined(id.getTimezone()) * 1000));
    }

    private int getValueOrZeroIfUndefined(int value) {
        return value == DatatypeConstants.FIELD_UNDEFINED ? 0 : value;
    }
}
