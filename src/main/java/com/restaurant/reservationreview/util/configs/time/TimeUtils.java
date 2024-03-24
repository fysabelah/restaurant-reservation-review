package com.restaurant.reservationreview.util.configs.time;

import com.restaurant.reservationreview.util.exception.ValidationsException;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public interface TimeUtils {

    static ZoneId getZoneId() {
        return ZoneId.of("America/Sao_Paulo");
    }

    static LocalDateTime getDate(String date) {
        return LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
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
}
