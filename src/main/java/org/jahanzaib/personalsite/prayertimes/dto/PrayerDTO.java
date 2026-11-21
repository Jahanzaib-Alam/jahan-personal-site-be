package org.jahanzaib.personalsite.prayertimes.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jahanzaib.personalsite.prayertimes.enums.PrayerName;

@Builder
@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class PrayerDTO {
  private final PrayerName name;
  private final LocalDateTime start;
  private final LocalDateTime jamat;
  private final boolean jamatChanging;
}
