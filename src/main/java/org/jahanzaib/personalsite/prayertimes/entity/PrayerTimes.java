package org.jahanzaib.personalsite.prayertimes.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class PrayerTimes {
    @Id private final Integer id;
    private final LocalDate date;
    private final LocalTime fajrStart;
    private final LocalTime sunrise;
    private final LocalTime dhuhrStart;
    private final LocalTime asrStart;
    private final LocalTime maghribStart;
    private final LocalTime ishaStart;
    private final LocalTime fajrJamat;
    private final LocalTime dhuhrJamat;
    private final LocalTime asrJamat;
    private final LocalTime maghribJamat;
    private final LocalTime ishaJamat;

    public Optional<LocalTime> getNextStartTime() {
        LocalTime now = LocalTime.now();

        return Stream.of(fajrStart, dhuhrStart, asrStart, maghribStart, ishaStart)
                .filter(time -> time.isAfter(now))
                .min(Comparator.naturalOrder());
    }

    public Optional<LocalTime> getNextJamatTime() {
        LocalTime now = LocalTime.now();

        return Stream.of(fajrJamat, dhuhrJamat, asrJamat, maghribJamat, ishaJamat)
                .filter(time -> time.isAfter(now))
                .min(Comparator.naturalOrder());
    }
}
