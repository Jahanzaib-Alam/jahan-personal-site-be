package org.jahanzaib.personalsite.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;

class HealthControllerTest {
  @Test
  void shouldReportLivenessAsUp() {
    HealthIndicator database = () -> Health.up().build();
    var controller = new HealthController(Map.of("database", database));

    assertThat(controller.liveness()).containsEntry("status", "UP");
  }

  @Test
  void shouldReportReadinessDownWhenDatabaseIsDown() {
    HealthIndicator database = () -> Health.down().build();
    var controller = new HealthController(Map.of("database", database));

    assertThat(controller.readiness().getStatusCode().value()).isEqualTo(503);
  }
}
