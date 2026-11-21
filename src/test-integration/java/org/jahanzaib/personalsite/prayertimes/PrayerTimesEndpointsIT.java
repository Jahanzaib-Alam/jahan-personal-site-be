package org.jahanzaib.personalsite.prayertimes;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import tools.jackson.databind.ObjectMapper;
import org.jahanzaib.personalsite.prayertimes.dto.CurrentPrayerTimesDTO;
import org.jahanzaib.personalsite.prayertimes.dto.PrayerDTO;
import org.jahanzaib.personalsite.prayertimes.dto.PrayerTimesDTO;
import org.jahanzaib.personalsite.prayertimes.enums.PrayerName;
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
  @Autowired private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
  }

  @Test
  void shouldReturnCurrentPrayerTimes() throws Exception {
    var currentTimes = CurrentPrayerTimes.builder().build();
    var fajr =
        PrayerDTO.builder()
            .name(PrayerName.FAJR)
            .start(LocalDateTime.of(2026, 5, 10, 4, 30))
            .jamat(LocalDateTime.of(2026, 5, 10, 5, 0))
            .jamatChanging(false)
            .build();
    var sunrise =
        PrayerDTO.builder()
            .name(PrayerName.SUNRISE)
            .start(LocalDateTime.of(2026, 5, 10, 6, 0))
            .jamatChanging(false)
            .build();
    var dhuhr =
        PrayerDTO.builder()
            .name(PrayerName.DHUHR)
            .start(LocalDateTime.of(2026, 5, 10, 12, 30))
            .jamat(LocalDateTime.of(2026, 5, 10, 13, 0))
            .jamatChanging(true)
            .build();
    var expected =
        CurrentPrayerTimesDTO.builder()
            .today(
                PrayerTimesDTO.builder()
                    .date(LocalDate.of(2026, 5, 10))
                    .prayers(List.of(fajr, sunrise, dhuhr))
                    .build())
            .tomorrow(
                PrayerTimesDTO.builder()
                    .date(LocalDate.of(2026, 5, 11))
                    .prayers(List.of(fajr, sunrise, dhuhr))
                    .build())
            .nextStart(fajr)
            .nextJamat(dhuhr)
            .jamatsChangingTomorrow(true)
            .prayerApiVersion("1.0.0")
            .build();
    when(prayerTimesService.getCurrentTimes(1)).thenReturn(currentTimes);
    when(prayerTimesMapper.toDto(currentTimes)).thenReturn(expected);

    var response =
        mockMvc
            .perform(get("/prayer-times/current/1"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse();
    var actual =
        objectMapper.readValue(response.getContentAsString(), CurrentPrayerTimesDTO.class);

    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void shouldReturnPrayerTimesForGivenDate() throws Exception {
    var date = LocalDate.of(2026, 5, 10);
    var namedTimes = NamedPrayerTimes.builder().date(date).prayers(List.of()).build();
    var fajr =
        PrayerDTO.builder()
            .name(PrayerName.FAJR)
            .start(LocalDateTime.of(2026, 5, 10, 4, 30))
            .jamat(LocalDateTime.of(2026, 5, 10, 5, 0))
            .jamatChanging(false)
            .build();
    var expected =
        PrayerTimesDTO.builder().date(date).prayers(List.of(fajr)).build();
    when(prayerTimesService.getTimesForDate(1, date)).thenReturn(namedTimes);
    when(prayerTimesMapper.toDto(namedTimes)).thenReturn(expected);

    var response =
        mockMvc
            .perform(get("/prayer-times/1/2026-05-10"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse();
    var actual = objectMapper.readValue(response.getContentAsString(), PrayerTimesDTO.class);

    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }
}
