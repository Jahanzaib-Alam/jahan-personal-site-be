package org.jahanzaib.personalsite.steam.converter;

import org.jahanzaib.personalsite.steam.dto.SteamInfoGameDTO;
import org.jahanzaib.personalsite.steam.dto.SteamInfoResponseDTO;
import org.jahanzaib.personalsite.steam.dto.steam.SteamGameDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SteamInfoConverter {
	private static final String STEAM_IMAGE_URL = "https://media.steampowered.com/steamcommunity/public/images/apps/{appId}/{imgIconId}.jpg";

	public SteamInfoResponseDTO steamGamesToInfoResponse(List<SteamGameDTO> games) {

		var infoGamesList = new ArrayList<SteamInfoGameDTO>();

		for (SteamGameDTO game : games) {
			var imageUrl = STEAM_IMAGE_URL.replace("{appId}", "" + game.appid()).replace("{imgIconId}", game.img_icon_url());

			var infoGame = new SteamInfoGameDTO(game.name(), game.playtime_forever(), imageUrl);
			infoGamesList.add(infoGame);
		}
		return new SteamInfoResponseDTO(infoGamesList);
	}
}
