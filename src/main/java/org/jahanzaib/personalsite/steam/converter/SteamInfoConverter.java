package org.jahanzaib.personalsite.steam.converter;

import org.jahanzaib.personalsite.steam.dto.SteamInfoGame;
import org.jahanzaib.personalsite.steam.dto.SteamInfoResponseDTO;
import org.jahanzaib.personalsite.steam.dto.steam.SteamGameDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SteamInfoConverter {
	public SteamInfoResponseDTO steamGamesToInfoResponse(List<SteamGameDTO> games) {

		var infoGamesList = new ArrayList<SteamInfoGame>();

		for (SteamGameDTO game : games) {
			var infoGame = new SteamInfoGame(game.name(), game.playtime_forever(), game.img_icon_url());
			infoGamesList.add(infoGame);
		}
		return new SteamInfoResponseDTO(infoGamesList);
	}
}
