package softuni.exam.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeAdapter extends XmlAdapter<String, LocalTime> {
    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public String marshal(LocalTime localTime) {
        return localTime.format(dateFormat);
    }

    @Override
    public LocalTime unmarshal(String localTime) {
        return LocalTime.parse(localTime, dateFormat);
    }
}
