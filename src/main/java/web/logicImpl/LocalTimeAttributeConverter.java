package web.logicImpl;

import java.sql.Time;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class LocalTimeAttributeConverter implements AttributeConverter<Time, Time> {
    
	@Override
    public Time convertToDatabaseColumn(Time localTime) {
        return localTime;
    }

    @Override
    public Time convertToEntityAttribute(Time sqlTime) {
        return sqlTime;
    }
}