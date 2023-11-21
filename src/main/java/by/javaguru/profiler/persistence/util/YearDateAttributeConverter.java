package by.javaguru.profiler.persistence.util;

import jakarta.persistence.AttributeConverter;

import java.sql.Date;
import java.time.Instant;
import java.time.MonthDay;
import java.time.Year;
import java.time.ZoneId;

public class YearDateAttributeConverter implements AttributeConverter<Year, Date> {

    @Override
    public Date convertToDatabaseColumn(Year attribute) {
        if (attribute != null) {
            return Date.valueOf(
                    attribute.atMonthDay(MonthDay.of(1, 1)));
        }
        return null;
    }

    @Override
    public Year convertToEntityAttribute(Date dbData) {
        if (dbData != null) {
            return Year.from(
                    Instant
                            .ofEpochMilli(dbData.getTime())
                            .atZone(ZoneId.of(PersistenceConstants.EUROPE_MINSK))
                            .toLocalDate());
        }
        return null;
    }
}
