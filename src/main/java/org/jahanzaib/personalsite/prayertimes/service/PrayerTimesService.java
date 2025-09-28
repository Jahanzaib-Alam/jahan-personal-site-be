package org.jahanzaib.personalsite.prayertimes.service;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.jahanzaib.personalsite.prayertimes.entity.CurrentPrayerTimes;
import org.jahanzaib.personalsite.prayertimes.repository.PrayerTimesRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrayerTimesService {

    private final PrayerTimesRepository repository;

    private static final int DEFAULT_YEAR = 2025;

    public CurrentPrayerTimes getCurrentTimes() {
        var today = LocalDate.now().withYear(DEFAULT_YEAR);
        return CurrentPrayerTimes.builder()
                .todayTimes(repository.getByDate(today))
                .tomorrowTimes(repository.getByDate(today.plusDays(1)))
                .build();
    }
}
