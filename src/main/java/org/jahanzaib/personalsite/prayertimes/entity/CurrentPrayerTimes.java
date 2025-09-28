package org.jahanzaib.personalsite.prayertimes.entity;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CurrentPrayerTimes {
    private final PrayerTimes todayTimes;
    private final PrayerTimes tomorrowTimes;
}
