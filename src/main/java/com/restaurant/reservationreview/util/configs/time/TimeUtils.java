package com.restaurant.reservationreview.util.configs.time;

import com.restaurant.reservationreview.util.exception.ValidationsException;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public interface TimeUtils {

    static ZoneId getZoneId() {
        return ZoneId.of("America/Sao_Paulo");
    }

    static LocalDateTime getDate(String date) {
        return LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                .atZone(getZoneId())
                .toLocalDateTime();
    }

    static LocalDate getDate(LocalDateTime localDateTime) {
        return LocalDate.from(localDateTime);
    }

    static LocalDateTime now() {
        Clock clock = Clock.system(getZoneId());

        return LocalDateTime.now(clock);
    }

    static LocalTime getTime(String time) throws ValidationsException {
        try {
            return LocalTime.parse(time, DateTimeFormatter.ISO_LOCAL_TIME);
        } catch (DateTimeParseException exception) {
            throw new ValidationsException("0003");
        }
    }

    static String getDayOfWeekInPortugues(DayOfWeek dayOfWeek) {
        return switch (dayOfWeek) {
            case MONDAY -> "Segunda";
            case TUESDAY -> "Ter\u00E7a";
            case WEDNESDAY -> "Quarta";
            case THURSDAY -> "Quinta";
            case FRIDAY -> "Sexta";
            case SATURDAY -> "S\u00E1bado";
            default -> "Domingo";
        };
    }

    static LocalDateTime addHoursToLocalDateTime(LocalDateTime dateTime, LocalTime time) {
        return dateTime.plusHours(time.getHour())
                .minusHours(time.getMinute())
                .plusSeconds(time.getSecond())
                .plusNanos(time.getNano());
    }
}
