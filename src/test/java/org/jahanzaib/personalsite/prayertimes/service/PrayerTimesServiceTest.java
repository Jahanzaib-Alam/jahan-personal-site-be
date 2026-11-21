package org.jahanzaib.personalsite.prayertimes.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.jahanzaib.personalsite.exception.ResourceNotFoundException;
import org.jahanzaib.personalsite.prayertimes.entity.PrayerTimes;
import org.jahanzaib.personalsite.prayertimes.enums.PrayerName;
import org.jahanzaib.personalsite.prayertimes.model.NamedPrayerTimes;
import org.jahanzaib.personalsite.prayertimes.model.Prayer;
import org.jahanzaib.personalsite.prayertimes.repository.MosqueRepository;
import org.jahanzaib.personalsite.prayertimes.repository.PrayerTimesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PrayerTimesServiceTest {
  @Mock private PrayerTimesRepository timesRepository;
  @Mock private MosqueRepository mosqueRepository;
  @Mock private PrayerTimesDstApplier dstApplier;
  @InjectMocks private PrayerTimesService service;

  @Test
  void shouldReturnPrayerTimesForGivenDate() {
    var date = LocalDate.of(2026, 5, 11);
    var times =
        PrayerTimes.builder()
            .mosqueId(7)
            .date(LocalDate.of(2024, 5, 11))
            .fajrStart(LocalTime.of(4, 30))
            .sunrise(LocalTime.of(5, 30))
            .dhuhrStart(LocalTime.of(12, 30))
            .asrStart(LocalTime.of(16, 30))
            .maghribStart(LocalTime.of(20, 30))
            .ishaStart(LocalTime.of(22, 0))
            .fajrJamat(LocalTime.of(5, 0))
            .dhuhrJamat(LocalTime.of(13, 0))
            .asrJamat(LocalTime.of(17, 0))
            .maghribJamat(LocalTime.of(20, 45))
            .ishaJamat(LocalTime.of(22, 30))
            .build();
    var expected =
        NamedPrayerTimes.builder()
            .date(times.getDate())
            .prayers(
                List.of(
                    Prayer.builder()
                        .name(PrayerName.FAJR)
                        .start(times.getFajrStart().atDate(date))
                        .jamat(times.getFajrJamat().atDate(date))
                        .build(),
                    Prayer.builder()
                        .name(PrayerName.SUNRISE)
                        .start(times.getSunrise().atDate(date))
                        .build(),
                    Prayer.builder()
                        .name(PrayerName.DHUHR)
                        .start(times.getDhuhrStart().atDate(date))
                        .jamat(times.getDhuhrJamat().atDate(date))
                        .build(),
                    Prayer.builder()
                        .name(PrayerName.ASR)
                        .start(times.getAsrStart().atDate(date))
                        .jamat(times.getAsrJamat().atDate(date))
                        .build(),
                    Prayer.builder()
                        .name(PrayerName.MAGHRIB)
                        .start(times.getMaghribStart().atDate(date))
                        .jamat(times.getMaghribJamat().atDate(date))
                        .build(),
                    Prayer.builder()
                        .name(PrayerName.ISHA)
                        .start(times.getIshaStart().atDate(date))
                        .jamat(times.getIshaJamat().atDate(date))
                        .build()))
            .build();
    when(timesRepository.findByDateAndMosqueId(date.withYear(2024), 7))
        .thenReturn(Optional.of(times));
    when(dstApplier.applyDaylightSavings(times, date)).thenReturn(times);

    var actual = service.getTimesForDate(7, date);

    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void shouldThrowNotFoundWhenPrayerTimesDoNotExist() {
    var date = LocalDate.of(2026, 5, 10);
    when(timesRepository.findByDateAndMosqueId(date.withYear(2024), 7))
        .thenReturn(Optional.empty());

    assertThatThrownBy(() -> service.getTimesForDate(7, date))
        .isInstanceOf(ResourceNotFoundException.class)
        .hasMessageContaining("mosque 7");
  }

  @Test
  void shouldThrowNotFoundWhenMosqueDoesNotExist() {
    var date = LocalDate.of(2026, 5, 15);
    var times =
        PrayerTimes.builder()
            .mosqueId(7)
            .date(LocalDate.of(2024, 5, 15))
            .fajrStart(LocalTime.of(4, 30))
            .sunrise(LocalTime.of(5, 30))
            .dhuhrStart(LocalTime.of(12, 30))
            .asrStart(LocalTime.of(16, 30))
            .maghribStart(LocalTime.of(20, 30))
            .ishaStart(LocalTime.of(22, 0))
            .fajrJamat(LocalTime.of(5, 0))
            .dhuhrJamat(LocalTime.of(13, 0))
            .asrJamat(LocalTime.of(17, 0))
            .maghribJamat(LocalTime.of(20, 45))
            .ishaJamat(LocalTime.of(22, 30))
            .build();
    when(timesRepository.findByDateAndMosqueId(date.withYear(2024), 7))
        .thenReturn(Optional.of(times));
    when(dstApplier.applyDaylightSavings(times, date)).thenReturn(times);
    when(mosqueRepository.findById(7)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> service.getTimesForDate(7, date))
        .isInstanceOf(ResourceNotFoundException.class)
        .hasMessage("Mosque not found: 7");
  }
}
