package org.jahanzaib.personalsite.steam.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record SteamInfoResponseDTO(List<SteamInfoGameDTO> games) {
  public SteamInfoResponseDTO {
    games = List.copyOf(games);
  }

  @Override
  public List<SteamInfoGameDTO> games() {
    return List.copyOf(games);
  }
}
