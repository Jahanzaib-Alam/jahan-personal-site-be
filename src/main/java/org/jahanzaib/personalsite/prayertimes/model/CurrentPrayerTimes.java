package org.jahanzaib.personalsite.prayertimes.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CurrentPrayerTimes {
    private final NamedPrayerTimes today;
    private final NamedPrayerTimes tomorrow;

    public Prayer getNextStart() {
        return today.getNextStart().orElseGet(tomorrow::getFajr);
    }

    public Prayer getNextJamat() {
        return today.getNextJamat().orElseGet(tomorrow::getFajr);
    }
}
