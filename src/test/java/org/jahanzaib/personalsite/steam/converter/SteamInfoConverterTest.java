package org.jahanzaib.personalsite.steam.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.jahanzaib.personalsite.steam.dto.SteamGameDTO;
import org.jahanzaib.personalsite.steam.dto.SteamInfoGameDTO;
import org.jahanzaib.personalsite.steam.dto.SteamInfoResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SteamInfoConverterTest {
  @InjectMocks private SteamInfoConverter converter;

  @Test
  void shouldConvertSteamGamesToApplicationDto() {
    var game =
        SteamGameDTO.builder()
            .appid(730)
            .name("Counter-Strike 2")
            .playtime_forever(125)
            .img_icon_url("icon")
            .build();
    var expected =
        SteamInfoResponseDTO.builder()
            .games(
                List.of(
                    SteamInfoGameDTO.builder()
                        .gameName("Counter-Strike 2")
                        .playTime(125)
                        .logoUrlId(
                            "https://media.steampowered.com/steamcommunity/public/images/apps/730/icon.jpg")
                        .build()))
            .build();

    var actual = converter.steamGamesToInfoResponse(List.of(game));

    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }
}
