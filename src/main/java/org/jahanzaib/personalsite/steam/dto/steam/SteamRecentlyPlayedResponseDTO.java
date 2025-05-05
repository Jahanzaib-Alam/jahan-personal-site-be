package org.jahanzaib.personalsite.steam.dto.steam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SteamRecentlyPlayedResponseDTO(
		SteamResponseNodeDTO response
) {}
