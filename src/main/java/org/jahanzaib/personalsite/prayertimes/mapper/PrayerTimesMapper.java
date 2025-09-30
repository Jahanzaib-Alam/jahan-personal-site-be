package org.jahanzaib.personalsite.prayertimes.mapper;

import org.jahanzaib.personalsite.prayertimes.dto.CurrentPrayerTimesDTO;
import org.jahanzaib.personalsite.prayertimes.dto.PrayerTimesDTO;
import org.jahanzaib.personalsite.prayertimes.entity.CurrentPrayerTimes;
import org.jahanzaib.personalsite.prayertimes.entity.PrayerTimes;
import org.springframework.stereotype.Component;

@Component
public class PrayerTimesMapper {

    public CurrentPrayerTimesDTO toDto(CurrentPrayerTimes currentTimes) {
        return CurrentPrayerTimesDTO.builder()
                .today(toDto(currentTimes.getToday()))
                .tomorrow(toDto(currentTimes.getTomorrow()))
                .build();
    }

    public PrayerTimesDTO toDto(PrayerTimes times) {
        return PrayerTimesDTO.builder()
                .date(times.getDate())
                .fajrStart(times.getFajrStart())
                .sunrise(times.getSunrise())
                .dhuhrStart(times.getDhuhrStart())
                .asrStart(times.getAsrStart())
                .maghribStart(times.getMaghribStart())
                .ishaStart(times.getIshaStart())
                .fajrJamat(times.getFajrJamat())
                .dhuhrJamat(times.getDhuhrJamat())
                .asrJamat(times.getAsrJamat())
                .maghribJamat(times.getMaghribJamat())
                .ishaJamat(times.getIshaJamat())
                .build();
    }
}
