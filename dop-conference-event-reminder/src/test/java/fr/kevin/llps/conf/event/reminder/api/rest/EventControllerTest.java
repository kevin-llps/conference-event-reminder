package fr.kevin.llps.conf.event.reminder.api.rest;

import fr.kevin.llps.conf.event.reminder.api.rest.dto.EventDto;
import fr.kevin.llps.conf.event.reminder.csv.CsvEvent;
import fr.kevin.llps.conf.event.reminder.service.EventFileParser;
import fr.kevin.llps.conf.event.reminder.service.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static fr.kevin.llps.conf.event.reminder.samples.BBLDtoSample.oneBBLDto;
import static fr.kevin.llps.conf.event.reminder.samples.CsvEventSample.csvEventList;
import static fr.kevin.llps.conf.event.reminder.samples.PracticeSessionDtoSample.onePracticeSessionDto;
import static fr.kevin.llps.conf.event.reminder.samples.TalkDtoSample.talkDtoList;
import static fr.kevin.llps.conf.event.reminder.utils.TestUtils.readResource;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventController.class)
class EventControllerTest {

    @Value("classpath:/csv/events.csv")
    private Resource events;

    @Value("classpath:/json/allEvents.json")
    private Resource upcomingEvents;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventFileParser eventFileParser;

    @MockBean
    private EventService eventService;

    @Test
    void shouldImportEvents() throws Exception {
        List<CsvEvent> csvEvents = csvEventList();

        MockMultipartFile fileToImport = new MockMultipartFile("file",
                "events.csv",
                MediaType.TEXT_PLAIN_VALUE,
                events.getInputStream());

        when(eventFileParser.parse(fileToImport)).thenReturn(csvEvents);
        doNothing().when(eventService).importEvents(csvEvents);

        mockMvc.perform(multipart("/events/import")
                        .file(fileToImport))
                .andExpect(status().isNoContent());

        verify(eventFileParser).parse(fileToImport);
        verify(eventService).importEvents(csvEvents);
        verifyNoMoreInteractions(eventFileParser, eventService);
    }

    @Test
    void shouldGetUpcomingEvents() throws Exception {
        List<EventDto> eventDtoList = new ArrayList<>(talkDtoList());
        eventDtoList.add(oneBBLDto());
        eventDtoList.add(onePracticeSessionDto());

        when(eventService.getUpcomingEvents()).thenReturn(eventDtoList);

        mockMvc.perform(get("/events/upcoming"))
                .andExpect(status().isOk())
                .andExpect(content().json(readResource(upcomingEvents)));

        verify(eventService).getUpcomingEvents();
        verifyNoMoreInteractions(eventService);
    }

    @Test
    void shouldExportEvents() throws Exception {
        byte[] bytes = events.getInputStream().readAllBytes();

        when(eventService.exportEvents()).thenReturn(bytes);

        mockMvc.perform(get("/events/export"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/csv;charset=UTF-8"))
                .andExpect(content().string(readResource(events)));

        verify(eventService).exportEvents();
        verifyNoMoreInteractions(eventService);
    }

}
