package org.jahanzaib.personalsite.steam.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.jahanzaib.personalsite.steam.client.SteamApiClient;
import org.jahanzaib.personalsite.steam.converter.SteamInfoConverter;
import org.jahanzaib.personalsite.steam.dto.SteamGameDTO;
import org.junit.jupiter.api.Test;

class SteamInfoServiceTest {
  private final SteamApiClient steamClient = mock(SteamApiClient.class);
  private final SteamInfoConverter converter = new SteamInfoConverter();
  private final SteamInfoService service = new SteamInfoService(steamClient, converter);

  @Test
  void shouldReturnEmptyResponseWhenSteamHasNoData() {
    when(steamClient.getRecentlyPlayedGames()).thenReturn(Optional.empty());

    var result = service.getSteamInfo();

    assertThat(result.games()).isEmpty();
  }

  @Test
  void shouldConvertSteamDataFromClient() {
    when(steamClient.getRecentlyPlayedGames())
        .thenReturn(Optional.of(List.of(new SteamGameDTO(10, "Game", 20, "icon"))));

    var result = service.getSteamInfo();

    assertThat(result.games()).hasSize(1);
  }
}
