package org.jahanzaib.personalsite.prayertimes.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CurrentPrayerTimesDTO {
    private final PrayerTimesDTO today;
    private final PrayerTimesDTO tomorrow;
    private final PrayerDTO nextStart;
    private final PrayerDTO nextJamat;
}
