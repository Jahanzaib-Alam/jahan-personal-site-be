package org.jahanzaib.personalsite.prayertimes.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.jahanzaib.personalsite.prayertimes.entity.PrayerTimes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PrayerTimesDstApplierTest {
  @InjectMocks private PrayerTimesDstApplier applier;

  @Test
  void shouldApplyOneHourForwardInMarch() {
    var times =
        PrayerTimes.builder()
            .fajrStart(LocalTime.of(5, 0))
            .sunrise(LocalTime.of(6, 30))
            .dhuhrStart(LocalTime.of(12, 0))
            .asrStart(LocalTime.of(15, 0))
            .maghribStart(LocalTime.of(18, 0))
            .ishaStart(LocalTime.of(19, 30))
            .fajrJamat(LocalTime.of(5, 30))
            .dhuhrJamat(LocalTime.of(13, 0))
            .asrJamat(LocalTime.of(15, 30))
            .maghribJamat(LocalTime.of(18, 30))
            .ishaJamat(LocalTime.of(20, 0))
            .build();

    var result = applier.applyDaylightSavings(times, LocalDate.of(2026, 3, 29));
    var expected =
        times.toBuilder()
            .fajrStart(LocalTime.of(6, 0))
            .sunrise(LocalTime.of(7, 30))
            .dhuhrStart(LocalTime.of(13, 0))
            .asrStart(LocalTime.of(16, 0))
            .maghribStart(LocalTime.of(19, 0))
            .ishaStart(LocalTime.of(20, 30))
            .fajrJamat(LocalTime.of(6, 30))
            .dhuhrJamat(LocalTime.of(14, 0))
            .asrJamat(LocalTime.of(16, 30))
            .maghribJamat(LocalTime.of(19, 30))
            .ishaJamat(LocalTime.of(21, 0))
            .build();

    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void shouldNotChangeTimesOutsideTransitionMonths() {
    var times = PrayerTimes.builder().fajrStart(LocalTime.of(5, 0)).build();
    var expected = times.toBuilder().build();

    var result = applier.applyDaylightSavings(times, LocalDate.of(2026, 7, 10));

    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void shouldDetectBritishSummerTime() {
    assertThat(applier.isDaylightSavings(LocalDate.of(2026, 7, 10))).isTrue();
    assertThat(applier.isDaylightSavings(LocalDate.of(2026, 1, 10))).isFalse();
  }
}
