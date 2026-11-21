package org.jahanzaib.personalsite.prayertimes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class CurrentPrayerTimesDTO {
  private final PrayerTimesDTO today;
  private final PrayerTimesDTO tomorrow;
  private final PrayerDTO nextStart;
  private final PrayerDTO nextJamat;
  private final boolean jamatsChangingTomorrow;
  private final String prayerApiVersion;
}
