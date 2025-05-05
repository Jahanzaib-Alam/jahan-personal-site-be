package org.jahanzaib.personalsite.steam.client;

import lombok.RequiredArgsConstructor;
import org.jahanzaib.personalsite.steam.dto.steam.SteamGameDTO;
import org.jahanzaib.personalsite.steam.dto.steam.SteamRecentlyPlayedResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SteamApiClient {
	private final RestTemplate restTemplate;

	private static final String STEAM_RECENT_GAMES_ENDPOINT = "https://api.steampowered.com/IPlayerService/GetRecentlyPlayedGames/v1/";

	@Value("${steam.api-key}")
	private String apiKey;
	@Value("${steam.user-id}")
	private String userId;

	public Optional<List<SteamGameDTO>> getRecentlyPlayedGames() {
		var url = STEAM_RECENT_GAMES_ENDPOINT + "?key=" + apiKey + "&steamid=" + userId + "&count=5";
		var headers = new HttpHeaders();
		var entity = new HttpEntity<>(headers);

		var response = restTemplate.exchange(url, HttpMethod.GET, entity, SteamRecentlyPlayedResponseDTO.class);

		if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
			return Optional.ofNullable(response.getBody().response().games());
		}

		return Optional.empty();
	}
}
