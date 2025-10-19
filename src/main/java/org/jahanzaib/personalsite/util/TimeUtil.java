package org.jahanzaib.personalsite.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeUtil {
    public static LocalTime nowUk() {
        return LocalTime.now(ZoneId.of("Europe/London"));
    }

    public static LocalDate nowDateUk() {
        return LocalDate.now(ZoneId.of("Europe/London"));
    }
}
