package org.jahanzaib.personalsite.prayertimes.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jahanzaib.personalsite.prayertimes.entity.PrayerTimes;
import org.jahanzaib.personalsite.prayertimes.enums.PrayerName;
import org.jahanzaib.personalsite.prayertimes.model.CurrentPrayerTimes;
import org.jahanzaib.personalsite.prayertimes.model.NamedPrayerTimes;
import org.jahanzaib.personalsite.prayertimes.model.Prayer;
import org.jahanzaib.personalsite.prayertimes.repository.MosqueRepository;
import org.jahanzaib.personalsite.prayertimes.repository.PrayerTimesRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrayerTimesService {

    private final PrayerTimesRepository repository;
    private final PrayerTimesDstApplier dstApplier;
    private final MosqueRepository mosqueRepository;

    private static final int DEFAULT_YEAR = 2024;

    public CurrentPrayerTimes getCurrentTimes(int mosqueId) {
        var today = LocalDate.now();
        var tomorrow = today.plusDays(1);
        return CurrentPrayerTimes.builder()
                .today(getTimesForDate(mosqueId, today))
                .tomorrow(getTimesForDate(mosqueId, tomorrow))
                .build();
    }

    public NamedPrayerTimes getTimesForDate(int mosqueId, LocalDate date) {
        var times =
                applyDst(
                        repository.getByDateAndMosqueId(date.withYear(DEFAULT_YEAR), mosqueId),
                        date);
        return toNamedTimes(times, date);
    }

    private PrayerTimes applyDst(PrayerTimes times, LocalDate date) {
        return dstApplier.applyDaylightSavings(times, date);
    }

    private NamedPrayerTimes toNamedTimes(PrayerTimes times, LocalDate date) {
        var fajr =
                Prayer.builder()
                        .name(PrayerName.FAJR)
                        .start(times.getFajrStart())
                        .jamat(times.getFajrJamat())
                        .build();
        var sunrise = Prayer.builder().name(PrayerName.SUNRISE).start(times.getSunrise()).build();
        var dhuhr = getDhuhr(date, times);
        var asr =
                Prayer.builder()
                        .name(PrayerName.ASR)
                        .start(times.getAsrStart())
                        .jamat(times.getAsrJamat())
                        .build();
        var maghrib =
                Prayer.builder()
                        .name(PrayerName.MAGHRIB)
                        .start(times.getMaghribStart())
                        .jamat(times.getMaghribJamat())
                        .build();
        var isha =
                Prayer.builder()
                        .name(PrayerName.ISHA)
                        .start(times.getIshaStart())
                        .jamat(times.getIshaJamat())
                        .build();
        return NamedPrayerTimes.builder()
                .date(times.getDate())
                .prayers(List.of(fajr, sunrise, dhuhr, asr, maghrib, isha))
                .build();
    }

    private Prayer getDhuhr(LocalDate date, PrayerTimes times) {
        var isFriday = date.getDayOfWeek() == DayOfWeek.FRIDAY;
        if (!isFriday) {
            return Prayer.builder()
                    .name(PrayerName.DHUHR)
                    .start(times.getDhuhrStart())
                    .jamat(times.getDhuhrJamat())
                    .build();
        }
        var mosque = mosqueRepository.getById(times.getMosqueId());
        var fridayJamatTime =
                dstApplier.isDaylightSavings(date)
                        ? mosque.getJumahTimeDst()
                        : mosque.getJumahTime();
        return Prayer.builder()
                .name(PrayerName.JUMAH)
                .start(times.getDhuhrStart())
                .jamat(fridayJamatTime)
                .build();
    }
}
