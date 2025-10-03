package org.jahanzaib.personalsite.prayertimes.dto;

import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;
import org.jahanzaib.personalsite.prayertimes.enums.PrayerName;

@Builder
@Getter
public class PrayerDTO {
    private final PrayerName name;
    private final LocalTime start;
    private final LocalTime jamat;
}
