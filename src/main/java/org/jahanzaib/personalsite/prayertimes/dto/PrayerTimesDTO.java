package org.jahanzaib.personalsite.prayertimes.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PrayerTimesDTO {
    private final LocalDate date;
    private final LocalTime fajrStart;
    private final LocalTime sunrise;
    private final LocalTime dhuhrStart;
    private final LocalTime asrStart;
    private final LocalTime maghribStart;
    private final LocalTime ishaStart;
    private final LocalTime fajrJamat;
    private final LocalTime dhuhrJamat;
    private final LocalTime asrJamat;
    private final LocalTime maghribJamat;
    private final LocalTime ishaJamat;
}
