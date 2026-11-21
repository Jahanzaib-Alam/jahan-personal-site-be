package org.jahanzaib.personalsite.steam.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.jahanzaib.personalsite.steam.dto.SteamGameDTO;
import org.junit.jupiter.api.Test;

class SteamInfoConverterTest {
  private final SteamInfoConverter converter = new SteamInfoConverter();

  @Test
  void shouldConvertSteamGamesToApplicationDto() {
    var game = new SteamGameDTO(730, "Counter-Strike 2", 125, "icon");

    var result = converter.steamGamesToInfoResponse(List.of(game));

    assertThat(result.games()).hasSize(1);
    assertThat(result.games().getFirst().gameName()).isEqualTo("Counter-Strike 2");
    assertThat(result.games().getFirst().logoUrlId()).contains("/730/icon.jpg");
  }
}
