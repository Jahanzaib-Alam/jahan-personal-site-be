package org.jahanzaib.personalsite.steam.dto;

public record SteamInfoGame(
		String gameName,
		int playTime,
		String logoUrlId
) {}
