package org.jahanzaib.personalsite.prayertimes.mapper;

import java.time.LocalDateTime;
import org.jahanzaib.personalsite.prayertimes.dto.CurrentPrayerTimesDTO;
import org.jahanzaib.personalsite.prayertimes.dto.PrayerDTO;
import org.jahanzaib.personalsite.prayertimes.dto.PrayerTimesDTO;
import org.jahanzaib.personalsite.prayertimes.enums.PrayerName;
import org.jahanzaib.personalsite.prayertimes.model.CurrentPrayerTimes;
import org.jahanzaib.personalsite.prayertimes.model.NamedPrayerTimes;
import org.jahanzaib.personalsite.prayertimes.model.Prayer;
import org.springframework.stereotype.Component;

@Component
public class PrayerTimesMapper {

    public CurrentPrayerTimesDTO toDto(CurrentPrayerTimes currentTimes) {
        var todayTimes = toDto(currentTimes.getToday(), currentTimes.getTomorrow());
        var tomorrowTimes = toDto(currentTimes.getTomorrow(), currentTimes.getToday());
        return CurrentPrayerTimesDTO.builder()
                .today(todayTimes)
                .tomorrow(tomorrowTimes)
                .nextStart(toDto(currentTimes.getNextStart()))
                .nextJamat(toDto(currentTimes.getNextJamat()))
                .jamatsChangingTomorrow(
                        tomorrowTimes.getPrayers().stream().anyMatch(PrayerDTO::isJamatChanging))
                .build();
    }

    public PrayerTimesDTO toDto(NamedPrayerTimes times) {
        return PrayerTimesDTO.builder()
                .date(times.getDate())
                .prayers(times.getPrayers().stream().map(this::toDto).toList())
                .build();
    }

    private PrayerTimesDTO toDto(NamedPrayerTimes times, NamedPrayerTimes otherDayTimes) {
        return PrayerTimesDTO.builder()
                .date(times.getDate())
                .prayers(
                        times.getPrayers().stream()
                                .map(prayer -> toDto(prayer, otherDayTimes))
                                .toList())
                .build();
    }

    private PrayerDTO toDto(Prayer prayer, NamedPrayerTimes otherTimes) {
        return PrayerDTO.builder()
                .name(prayer.getName())
                .start(prayer.getStart())
                .jamat(prayer.getJamat())
                .jamatChanging(isJamatChanging(prayer, otherTimes))
                .build();
    }

    private Boolean isJamatChanging(Prayer prayer, NamedPrayerTimes otherTimes) {
        return otherTimes.getPrayers().stream()
                .filter(
                        todayPrayer ->
                                todayPrayer.getName().equals(prayer.getName())
                                        && !prayer.getName().equals(PrayerName.MAGHRIB))
                .findFirst()
                .map(Prayer::getJamat)
                .map(LocalDateTime::toLocalTime)
                .map(jamat -> !jamat.equals(prayer.getJamat().toLocalTime()))
                .orElse(false);
    }

    private PrayerDTO toDto(Prayer prayer) {
        return PrayerDTO.builder()
                .name(prayer.getName())
                .start(prayer.getStart())
                .jamat(prayer.getJamat())
                .build();
    }
}
