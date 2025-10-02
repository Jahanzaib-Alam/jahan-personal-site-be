package org.jahanzaib.personalsite.prayertimes.dto;

import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CurrentPrayerTimesDTO {
    private final PrayerTimesDTO today;
    private final PrayerTimesDTO tomorrow;
    private final LocalTime nextStart;
    private final LocalTime nextJamat;
}
