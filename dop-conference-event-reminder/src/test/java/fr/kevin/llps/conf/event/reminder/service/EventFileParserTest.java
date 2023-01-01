package fr.kevin.llps.conf.event.reminder.service;

import fr.kevin.llps.conf.event.reminder.csv.CsvEvent;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.List;

import static fr.kevin.llps.conf.event.reminder.samples.CsvEventSample.csvEventList;
import static org.assertj.core.api.Assertions.assertThat;

class EventFileParserTest {

    @Test
    void shouldParseMultipartFile() throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:/csv/events.csv");

        List<CsvEvent> expectedCsvEvents = csvEventList();

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file",
                "events.csv",
                MediaType.TEXT_PLAIN_VALUE,
                resource.getInputStream());

        EventFileParser eventFileParser = new EventFileParser();

        List<CsvEvent> csvEvents = eventFileParser.parse(mockMultipartFile);

        assertThat(csvEvents).containsExactlyInAnyOrderElementsOf(expectedCsvEvents);
    }

}
