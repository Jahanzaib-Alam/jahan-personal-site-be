package org.jahanzaib.personalsite.steam.dto.steam;

import java.util.List;

public record SteamResponseNodeDTO(
		List<SteamGameDTO> games
) {}
