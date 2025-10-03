package org.jahanzaib.personalsite.prayertimes.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Mosque {
    @Id private final Integer id;
    private final String name;
    private final LocalTime jumahTime;
    private final LocalTime jumahTimeDst;
}
