package org.jahanzaib.personalsite.steam.dto.steam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SteamGameDTO(
		int appid,
		String name,
		int playtime_forever,
		String img_icon_url
) {
}
