package org.jahanzaib.personalsite.steam.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.jahanzaib.personalsite.steam.client.SteamApiClient;
import org.jahanzaib.personalsite.steam.converter.SteamInfoConverter;
import org.jahanzaib.personalsite.steam.dto.SteamGameDTO;
import org.jahanzaib.personalsite.steam.dto.SteamInfoGameDTO;
import org.jahanzaib.personalsite.steam.dto.SteamInfoResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SteamInfoServiceTest {
  @Mock private SteamApiClient steamClient;
  @Mock private SteamInfoConverter converter;
  @InjectMocks private SteamInfoService service;

  @Test
  void shouldReturnEmptyResponseWhenSteamHasNoData() {
    when(steamClient.getRecentlyPlayedGames()).thenReturn(Optional.empty());
    var expected = SteamInfoResponseDTO.builder().games(List.of()).build();

    var actual = service.getSteamInfo();

    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void shouldConvertSteamDataFromClient() {
    var steamGame =
        SteamGameDTO.builder()
            .appid(10)
            .name("Game")
            .playtime_forever(20)
            .img_icon_url("icon")
            .build();
    var steamGames = List.of(steamGame);
    var expected =
        SteamInfoResponseDTO.builder()
            .games(
                List.of(
                    SteamInfoGameDTO.builder()
                        .gameName("Game")
                        .playTime(20)
                        .logoUrlId("https://example.com/icon.jpg")
                        .build()))
            .build();
    when(steamClient.getRecentlyPlayedGames()).thenReturn(Optional.of(steamGames));
    when(converter.steamGamesToInfoResponse(steamGames)).thenReturn(expected);

    var actual = service.getSteamInfo();

    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }
}
