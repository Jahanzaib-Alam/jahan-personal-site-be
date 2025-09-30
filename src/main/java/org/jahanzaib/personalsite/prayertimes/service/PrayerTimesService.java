package org.jahanzaib.personalsite.prayertimes.service;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.jahanzaib.personalsite.prayertimes.entity.CurrentPrayerTimes;
import org.jahanzaib.personalsite.prayertimes.entity.PrayerTimes;
import org.jahanzaib.personalsite.prayertimes.repository.PrayerTimesRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrayerTimesService {

    private final PrayerTimesRepository repository;
    private final PrayerTimesDstApplier dstApplier;

    private static final int DEFAULT_YEAR = 2024;

    public CurrentPrayerTimes getCurrentTimes() {
        var today = LocalDate.now();
        var tomorrow = today.plusDays(1);
        return CurrentPrayerTimes.builder()
                .today(applyDst(repository.getByDate(today.withYear(DEFAULT_YEAR)), today))
                .tomorrow(applyDst(repository.getByDate(tomorrow.withYear(DEFAULT_YEAR)), tomorrow))
                .build();
    }

    public PrayerTimes getTimesForDate(LocalDate date) {
        return applyDst(repository.getByDate(date.withYear(DEFAULT_YEAR)), date);
    }

    private PrayerTimes applyDst(PrayerTimes times, LocalDate date) {
        return dstApplier.applyDaylightSavings(times, date);
    }
}
