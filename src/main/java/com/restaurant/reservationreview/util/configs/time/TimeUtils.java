package com.restaurant.reservationreview.util.configs.time;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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
}
