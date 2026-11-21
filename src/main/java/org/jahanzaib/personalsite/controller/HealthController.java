package org.jahanzaib.personalsite.controller;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.boot.health.contributor.Status;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healthz")
@RequiredArgsConstructor
public class HealthController {
  private final Map<String, HealthIndicator> healthIndicators;

  @GetMapping
  public ResponseEntity<Map<String, Object>> health() {
    var health = aggregate();
    return Status.UP.equals(health.getStatus())
        ? ResponseEntity.ok(Map.of("status", "UP"))
        : ResponseEntity.status(503).body(Map.of("status", "DOWN"));
  }

  @GetMapping("/liveness")
  public Map<String, String> liveness() {
    return Map.of("status", "UP");
  }

  @GetMapping("/readiness")
  public ResponseEntity<Map<String, Object>> readiness() {
    var health = aggregate();
    return Status.UP.equals(health.getStatus())
        ? ResponseEntity.ok(Map.of("status", "UP"))
        : ResponseEntity.status(503).body(Map.of("status", "DOWN"));
  }

  private Health aggregate() {
    for (var indicator : healthIndicators.values()) {
      var health = indicator.health();
      if (health == null) {
        return Health.down().build();
      }
      if (!Status.UP.equals(health.getStatus())) {
        return health;
      }
    }
    return Health.up().build();
  }
}
