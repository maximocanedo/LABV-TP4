package logicImpl;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalTimeAttributeConverter implements AttributeConverter<LocalTime, String> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public String convertToDatabaseColumn(LocalTime locTime) {
        return locTime == null ? null : locTime.format(FORMATTER);
    }

    @Override
    public LocalTime convertToEntityAttribute(String sqlTime) {
        return sqlTime == null ? null : LocalTime.parse(sqlTime, FORMATTER);
    }
}
