package org.jahanzaib.personalsite.steam.controller;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.jahanzaib.personalsite.steam.dto.SteamInfoResponseDTO;
import org.jahanzaib.personalsite.steam.service.SteamInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/steam")
@RequiredArgsConstructor
public class SteamInfoController {
	private final SteamInfoService service;

	@GetMapping("/info")
	@RateLimiter(name = "steamApiLimiter")
	public ResponseEntity<SteamInfoResponseDTO> getSteamInfo() {
		return ResponseEntity.ok(service.getSteamInfo());
	}
}
