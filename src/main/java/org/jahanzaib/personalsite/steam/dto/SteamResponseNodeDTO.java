package org.jahanzaib.personalsite.steam.dto;

import java.util.List;

public record SteamResponseNodeDTO(
		List<SteamGameDTO> games
) {}
