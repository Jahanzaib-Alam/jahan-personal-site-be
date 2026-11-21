package org.jahanzaib.personalsite.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseHealthIndicator implements HealthIndicator {
  private final JdbcTemplate jdbcTemplate;

  @Override
  public Health health() {
    try {
      jdbcTemplate.queryForObject("SELECT 1", Integer.class);
      return Health.up().withDetail("database", "reachable").build();
    } catch (RuntimeException exception) {
      return Health.down(exception).build();
    }
  }
}
