package org.jahanzaib.personalsite.steam;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.jahanzaib.personalsite.steam.dto.SteamInfoGameDTO;
import org.jahanzaib.personalsite.steam.dto.SteamInfoResponseDTO;
import org.jahanzaib.personalsite.steam.service.SteamInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles("test")
@SpringBootTest
class SteamEndpointsIT {

  @Autowired private WebApplicationContext applicationContext;

  @MockitoBean private SteamInfoService steamInfoService;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
  }

  @Test
  void shouldReturnSteamInfo() throws Exception {
    var response =
        SteamInfoResponseDTO.builder()
            .games(List.of(new SteamInfoGameDTO("Counter-Strike 2", 125, "/730/icon.jpg")))
            .build();
    when(steamInfoService.getSteamInfo()).thenReturn(response);

    mockMvc
        .perform(get("/steam/info"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.games[0].gameName").value("Counter-Strike 2"));
  }
}
