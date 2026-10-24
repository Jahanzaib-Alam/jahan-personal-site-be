package org.jahanzaib.personalsite.steam.client;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jahanzaib.personalsite.steam.dto.SteamGameDTO;
import org.jahanzaib.personalsite.steam.dto.SteamRecentlyPlayedResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class SteamApiClient {
  private final RestClient restClient;

  private static final String STEAM_RECENT_GAMES_ENDPOINT =
      "https://api.steampowered.com/IPlayerService/GetRecentlyPlayedGames/v1/";

  @Value("${steam.api-key}")
  private String apiKey;

  @Value("${steam.user-id}")
  private String userId;

  public Optional<List<SteamGameDTO>> getRecentlyPlayedGames() {
    var url = STEAM_RECENT_GAMES_ENDPOINT + "?key=" + apiKey + "&steamid=" + userId + "&count=5";

    var responseDto =
        restClient.get().uri(url).retrieve().body(SteamRecentlyPlayedResponseDTO.class);

    if (responseDto != null) {
      return Optional.ofNullable(responseDto.response().games());
    }

    return Optional.empty();
  }
}
