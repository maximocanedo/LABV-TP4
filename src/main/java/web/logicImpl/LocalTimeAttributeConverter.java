package web.logicImpl;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class LocalTimeAttributeConverter implements AttributeConverter<LocalTime, String> {
    
    /**/
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public String convertToDatabaseColumn(LocalTime locTime) {
        return locTime == null ? null : locTime.format(FORMATTER);
    }

    @Override
    public LocalTime convertToEntityAttribute(String sqlTime) {
        return sqlTime == null ? null : LocalTime.parse(sqlTime, FORMATTER);
    } //*/
}