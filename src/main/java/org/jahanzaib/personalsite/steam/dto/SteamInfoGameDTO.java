package org.jahanzaib.personalsite.steam.dto;

import lombok.Builder;

@Builder
public record SteamInfoGameDTO(String gameName, int playTime, String logoUrlId) {}
