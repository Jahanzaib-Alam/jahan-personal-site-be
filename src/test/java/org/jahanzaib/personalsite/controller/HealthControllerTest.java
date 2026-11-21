package org.jahanzaib.personalsite.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class HealthControllerTest {
  @Mock private Map<String, HealthIndicator> healthIndicators;
  @InjectMocks private HealthController controller;

  @Test
  void shouldReportLivenessAsUp() {
    var expected = Map.of("status", "UP");

    var actual = controller.liveness();

    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void shouldReportReadinessDownWhenDatabaseIsDown() {
    HealthIndicator database = () -> Health.down().build();
    when(healthIndicators.values()).thenReturn(Set.of(database));
    var expected = ResponseEntity.status(503).body(Map.of("status", "DOWN"));

    var actual = controller.readiness();

    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }
}
