package org.jahanzaib.personalsite.prayertimes.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class PrayerTimesDTO {
  private final LocalDate date;
  private final List<PrayerDTO> prayers;
}
