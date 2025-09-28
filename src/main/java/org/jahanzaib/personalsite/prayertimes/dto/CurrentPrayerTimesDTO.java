package org.jahanzaib.personalsite.prayertimes.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CurrentPrayerTimesDTO {
    private final PrayerTimesDTO todayTimes;
    private final PrayerTimesDTO tomorrowTimes;
}
