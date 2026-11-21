package org.jahanzaib.personalsite.prayertimes;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;
import org.jahanzaib.personalsite.prayertimes.dto.CurrentPrayerTimesDTO;
import org.jahanzaib.personalsite.prayertimes.dto.PrayerTimesDTO;
import org.jahanzaib.personalsite.prayertimes.mapper.PrayerTimesMapper;
import org.jahanzaib.personalsite.prayertimes.model.CurrentPrayerTimes;
import org.jahanzaib.personalsite.prayertimes.model.NamedPrayerTimes;
import org.jahanzaib.personalsite.prayertimes.service.PrayerTimesService;
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
class PrayerTimesEndpointsIT {

  @Autowired private WebApplicationContext applicationContext;

  @MockitoBean private PrayerTimesService prayerTimesService;
  @MockitoBean private PrayerTimesMapper prayerTimesMapper;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
  }

  @Test
  void shouldReturnCurrentPrayerTimes() throws Exception {
    var currentTimes = CurrentPrayerTimes.builder().build();
    var response =
        CurrentPrayerTimesDTO.builder()
            .prayerApiVersion("1.0.0")
            .today(PrayerTimesDTO.builder().date(LocalDate.of(2026, 5, 10)).build())
            .tomorrow(PrayerTimesDTO.builder().date(LocalDate.of(2026, 5, 11)).build())
            .build();
    when(prayerTimesService.getCurrentTimes(1)).thenReturn(currentTimes);
    when(prayerTimesMapper.toDto(currentTimes)).thenReturn(response);

    mockMvc
        .perform(get("/prayer-times/current/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.prayerApiVersion").value("1.0.0"));
  }

  @Test
  void shouldReturnPrayerTimesForGivenDate() throws Exception {
    var date = LocalDate.of(2026, 5, 10);
    var namedTimes = NamedPrayerTimes.builder().date(date).prayers(List.of()).build();
    var response = PrayerTimesDTO.builder().date(date).prayers(List.of()).build();
    when(prayerTimesService.getTimesForDate(1, date)).thenReturn(namedTimes);
    when(prayerTimesMapper.toDto(namedTimes)).thenReturn(response);

    mockMvc
        .perform(get("/prayer-times/1/2026-05-10"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.date").value("2026-05-10"));
  }
}
