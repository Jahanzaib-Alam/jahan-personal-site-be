package org.jahanzaib.personalsite.prayertimes.repository;

import java.time.LocalDate;
import java.util.Optional;
import org.jahanzaib.personalsite.prayertimes.entity.PrayerTimes;
import org.springframework.data.repository.Repository;

public interface PrayerTimesRepository extends Repository<PrayerTimes, Integer> {
  Optional<PrayerTimes> findByDateAndMosqueId(LocalDate date, int mosqueId);
}
