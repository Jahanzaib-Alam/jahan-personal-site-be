package org.jahanzaib.personalsite.prayertimes.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.jahanzaib.personalsite.prayertimes.entity.PrayerTimes;
import org.junit.jupiter.api.Test;

class PrayerTimesDstApplierTest {
  private final PrayerTimesDstApplier applier = new PrayerTimesDstApplier();

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

    assertThat(result.getFajrStart()).isEqualTo(LocalTime.of(6, 0));
  }

  @Test
  void shouldNotChangeTimesOutsideTransitionMonths() {
    var times = PrayerTimes.builder().fajrStart(LocalTime.of(5, 0)).build();

    var result = applier.applyDaylightSavings(times, LocalDate.of(2026, 7, 10));

    assertThat(result).isSameAs(times);
  }

  @Test
  void shouldDetectBritishSummerTime() {
    assertThat(applier.isDaylightSavings(LocalDate.of(2026, 7, 10))).isTrue();
    assertThat(applier.isDaylightSavings(LocalDate.of(2026, 1, 10))).isFalse();
  }
}
