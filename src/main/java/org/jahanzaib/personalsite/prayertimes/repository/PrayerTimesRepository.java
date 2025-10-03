package org.jahanzaib.personalsite.prayertimes.repository;

import java.time.LocalDate;
import org.jahanzaib.personalsite.prayertimes.entity.PrayerTimes;
import org.springframework.data.repository.Repository;

public interface PrayerTimesRepository extends Repository<PrayerTimes, Integer> {
    PrayerTimes getByDateAndMosqueId(LocalDate date, int mosqueId);
}
