package org.jahanzaib.personalsite.steam.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public record SteamGameDTO(int appid, String name, int playtime_forever, String img_icon_url) {}
