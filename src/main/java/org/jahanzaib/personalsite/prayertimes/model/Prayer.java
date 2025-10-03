package org.jahanzaib.personalsite.prayertimes.model;

import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;
import org.jahanzaib.personalsite.prayertimes.enums.PrayerName;

@Builder
@Getter
public class Prayer {
    private final PrayerName name;
    private final LocalTime start;
    private final LocalTime jamat;
}
