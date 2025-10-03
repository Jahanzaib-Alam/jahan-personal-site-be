package org.jahanzaib.personalsite.prayertimes.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PrayerTimesDTO {
    private final LocalDate date;
    private final List<PrayerDTO> prayers;
}
