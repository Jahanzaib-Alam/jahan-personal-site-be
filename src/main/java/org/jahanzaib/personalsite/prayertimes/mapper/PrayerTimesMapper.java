package org.jahanzaib.personalsite.prayertimes.mapper;

import org.jahanzaib.personalsite.prayertimes.dto.CurrentPrayerTimesDTO;
import org.jahanzaib.personalsite.prayertimes.dto.PrayerDTO;
import org.jahanzaib.personalsite.prayertimes.dto.PrayerTimesDTO;
import org.jahanzaib.personalsite.prayertimes.model.CurrentPrayerTimes;
import org.jahanzaib.personalsite.prayertimes.model.NamedPrayerTimes;
import org.jahanzaib.personalsite.prayertimes.model.Prayer;
import org.springframework.stereotype.Component;

@Component
public class PrayerTimesMapper {

    public CurrentPrayerTimesDTO toDto(CurrentPrayerTimes currentTimes) {
        return CurrentPrayerTimesDTO.builder()
                .today(toDto(currentTimes.getToday()))
                .tomorrow(toDto(currentTimes.getTomorrow()))
                .nextStart(toDto(currentTimes.getNextStart()))
                .nextJamat(toDto(currentTimes.getNextJamat()))
                .build();
    }

    public PrayerTimesDTO toDto(NamedPrayerTimes times) {
        return PrayerTimesDTO.builder()
                .date(times.getDate())
                .prayers(times.getPrayers().stream().map(this::toDto).toList())
                .build();
    }

    private PrayerDTO toDto(Prayer prayer) {
        return PrayerDTO.builder()
                .name(prayer.getName())
                .start(prayer.getStart())
                .jamat(prayer.getJamat())
                .build();
    }
}
