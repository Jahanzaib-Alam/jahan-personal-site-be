package org.jahanzaib.personalsite.prayertimes.service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import lombok.RequiredArgsConstructor;
import org.jahanzaib.personalsite.prayertimes.entity.PrayerTimes;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PrayerTimesDstApplier {

    private final Clock clock;

    public PrayerTimes applyDaylightSavings(PrayerTimes times, LocalDate date) {
        int dstOffset = calculateDstOffset(date);
        return dstOffset == 0 ? times : applyDaylightSavings(times, dstOffset);
    }

    private int calculateDstOffset(LocalDate date) {
        boolean isDst = isDaylightSavings(date);
        if (date.getMonth() == Month.MARCH && isDst) { // March transition: clocks go forward
            return +1;
        }
        if (date.getMonth() == Month.OCTOBER && !isDst) { // October transition: clocks go back
            return -1;
        }
        return 0;
    }

    private PrayerTimes applyDaylightSavings(PrayerTimes times, int dstOffset) {
        return PrayerTimes.builder()
                .date(times.getDate())
                .fajrStart(times.getFajrStart().plusHours(dstOffset))
                .sunrise(times.getSunrise().plusHours(dstOffset))
                .dhuhrStart(times.getDhuhrStart().plusHours(dstOffset))
                .asrStart(times.getAsrStart().plusHours(dstOffset))
                .maghribStart(times.getMaghribStart().plusHours(dstOffset))
                .ishaStart(times.getIshaStart().plusHours(dstOffset))
                .fajrJamat(times.getFajrJamat().plusHours(dstOffset))
                .dhuhrJamat(times.getDhuhrJamat().plusHours(dstOffset))
                .asrJamat(times.getAsrJamat().plusHours(dstOffset))
                .maghribJamat(times.getMaghribJamat().plusHours(dstOffset))
                .ishaJamat(times.getIshaJamat().plusHours(dstOffset))
                .build();
    }

    public boolean isDaylightSavings(LocalDate date) {
        var ukZone = ZoneId.of("Europe/London");
        return ukZone.getRules().isDaylightSavings(date.atTime(12, 0).atZone(ukZone).toInstant());
    }
}
