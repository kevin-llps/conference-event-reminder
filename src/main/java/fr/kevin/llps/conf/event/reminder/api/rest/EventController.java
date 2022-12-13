package fr.kevin.llps.conf.event.reminder.api.rest;

import fr.kevin.llps.conf.event.reminder.domain.Talk;
import fr.kevin.llps.conf.event.reminder.service.TalkFileParser;
import fr.kevin.llps.conf.event.reminder.service.TalkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final TalkFileParser talkFileParser;
    private final TalkService talkService;

    @PostMapping("/events/import")
    @ResponseStatus(NO_CONTENT)
    public void importEvents(@RequestParam("file") MultipartFile fileToImport) throws IOException {
        List<Talk> talks = talkFileParser.parse(fileToImport);

        talkService.importTalks(talks);
    }

}
