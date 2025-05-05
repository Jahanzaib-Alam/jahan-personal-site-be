package org.jahanzaib.personalsite.steam.dto;

import java.util.List;

public record SteamInfoResponseDTO(
		List<SteamInfoGame> games
) {}
