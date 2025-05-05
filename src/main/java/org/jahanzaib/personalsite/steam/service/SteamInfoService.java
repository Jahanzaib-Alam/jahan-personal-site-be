package org.jahanzaib.personalsite.steam.service;

import lombok.RequiredArgsConstructor;
import org.jahanzaib.personalsite.steam.client.SteamApiClient;
import org.jahanzaib.personalsite.steam.converter.SteamInfoConverter;
import org.jahanzaib.personalsite.steam.dto.SteamInfoResponseDTO;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class SteamInfoService {

	private final SteamApiClient steamClient;
	private final SteamInfoConverter steamInfoConverter;

	public SteamInfoResponseDTO getSteamInfo() {
		var recentlyPlayedGames = steamClient.getRecentlyPlayedGames();
		return recentlyPlayedGames.map(steamInfoConverter::steamGamesToInfoResponse)
				.orElse(new SteamInfoResponseDTO(Collections.emptyList()));
	}

}
