package org.jahanzaib.personalsite.prayertimes.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;
import org.jahanzaib.personalsite.prayertimes.enums.PrayerName;

@Builder
@Getter
public class NamedPrayerTimes {
    private final LocalDate date;
    private final List<Prayer> prayers;

    public Optional<Prayer> getNextStart() {
        LocalTime now = LocalTime.now();

        return prayers.stream()
                .filter(prayer -> prayer.getStart().isAfter(now))
                .min(Comparator.comparing(Prayer::getStart));
    }

    public Optional<Prayer> getNextJamat() {
        LocalTime now = LocalTime.now();

        return prayers.stream()
                .filter(prayer -> prayer.getStart().isAfter(now))
                .min(Comparator.comparing(Prayer::getJamat));
    }

    public Prayer getFajr() {
        return prayers.stream()
                .filter(prayer -> PrayerName.FAJR.equals(prayer.getName()))
                .findFirst()
                .orElse(null);
    }
}
