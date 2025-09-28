package org.jahanzaib.personalsite.prayertimes.controller;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.jahanzaib.personalsite.prayertimes.dto.CurrentPrayerTimesDTO;
import org.jahanzaib.personalsite.prayertimes.mapper.PrayerTimesMapper;
import org.jahanzaib.personalsite.prayertimes.service.PrayerTimesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prayer-times")
@RequiredArgsConstructor
public class PrayerTimesController {

    private final PrayerTimesService service;
    private final PrayerTimesMapper mapper;

    @RateLimiter(name = "prayerTimesApiLimiter")
    @GetMapping("/current")
    public ResponseEntity<CurrentPrayerTimesDTO> getSteamInfo() {
        return ResponseEntity.ok(mapper.toDto(service.getCurrentTimes()));
    }
}
