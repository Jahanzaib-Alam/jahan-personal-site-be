package org.jahanzaib.personalsite.prayertimes.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;
import org.jahanzaib.personalsite.exception.ResourceNotFoundException;
import org.jahanzaib.personalsite.prayertimes.repository.MosqueRepository;
import org.jahanzaib.personalsite.prayertimes.repository.PrayerTimesRepository;
import org.junit.jupiter.api.Test;

class PrayerTimesServiceTest {
  private final PrayerTimesRepository timesRepository = mock(PrayerTimesRepository.class);
  private final MosqueRepository mosqueRepository = mock(MosqueRepository.class);
  private final PrayerTimesDstApplier dstApplier = mock(PrayerTimesDstApplier.class);
  private final PrayerTimesService service =
      new PrayerTimesService(timesRepository, dstApplier, mosqueRepository);

  @Test
  void shouldThrowNotFoundWhenPrayerTimesDoNotExist() {
    var date = LocalDate.of(2026, 5, 10);
    when(timesRepository.findByDateAndMosqueId(date.withYear(2024), 7))
        .thenReturn(Optional.empty());

    assertThatThrownBy(() -> service.getTimesForDate(7, date))
        .isInstanceOf(ResourceNotFoundException.class)
        .hasMessageContaining("mosque 7");
  }
}
