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
        var today = LocalDate.now().withYear(DEFAULT_YEAR);
        var tomorrow = today.plusDays(1);
        return CurrentPrayerTimes.builder()
                .today(dstApplier.applyDaylightSavings(repository.getByDate(today), today))
                .tomorrow(dstApplier.applyDaylightSavings(repository.getByDate(tomorrow), tomorrow))
                .build();
    }

    public PrayerTimes getTimesForDate(LocalDate date) {
        return dstApplier.applyDaylightSavings(
                repository.getByDate(date.withYear(DEFAULT_YEAR)), date);
    }
}
