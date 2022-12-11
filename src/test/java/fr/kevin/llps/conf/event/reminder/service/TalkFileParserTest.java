package fr.kevin.llps.conf.event.reminder.service;

import fr.kevin.llps.conf.event.reminder.domain.Talk;
import fr.kevin.llps.conf.event.reminder.samples.TalkSample;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TalkFileParserTest {

    @Test
    void shouldParseMultipartFile() throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:/csv/events.csv");

        List<Talk> expectedTalks = TalkSample.talkList();

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file",
                "events.csv",
                MediaType.TEXT_PLAIN_VALUE,
                resource.getInputStream());

        TalkFileParser talkFileParser = new TalkFileParser();

        List<Talk> talks = talkFileParser.parse(mockMultipartFile);

        assertThat(talks).containsExactlyInAnyOrderElementsOf(expectedTalks);
    }

}
