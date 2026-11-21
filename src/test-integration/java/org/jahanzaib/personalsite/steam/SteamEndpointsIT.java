package org.jahanzaib.personalsite.steam;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import tools.jackson.databind.ObjectMapper;
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
  @Autowired private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
  }

  @Test
  void shouldReturnSteamInfo() throws Exception {
    var expected =
        SteamInfoResponseDTO.builder()
            .games(
                List.of(
                    SteamInfoGameDTO.builder()
                        .gameName("Counter-Strike 2")
                        .playTime(125)
                        .logoUrlId("/730/icon.jpg")
                        .build()))
            .build();
    when(steamInfoService.getSteamInfo()).thenReturn(expected);

    var response =
        mockMvc
            .perform(get("/steam/info"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse();
    var actual =
        objectMapper.readValue(response.getContentAsString(), SteamInfoResponseDTO.class);

    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }
}
