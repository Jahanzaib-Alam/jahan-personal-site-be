package org.jahanzaib.personalsite.prayertimes.entity;

import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CurrentPrayerTimes {
    private final PrayerTimes today;
    private final PrayerTimes tomorrow;

    public LocalTime getNextStartTime() {
        return today.getNextStartTime().orElseGet(tomorrow::getFajrStart);
    }

    public LocalTime getNextJamatTime() {
        return today.getNextJamatTime().orElseGet(tomorrow::getFajrJamat);
    }
}
