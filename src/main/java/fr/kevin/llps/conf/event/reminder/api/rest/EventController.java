package fr.kevin.llps.conf.event.reminder.api.rest;

import fr.kevin.llps.conf.event.reminder.api.rest.dto.TalkDto;
import fr.kevin.llps.conf.event.reminder.api.rest.mapper.TalkMapper;
import fr.kevin.llps.conf.event.reminder.csv.CsvEvent;
import fr.kevin.llps.conf.event.reminder.domain.Talk;
import fr.kevin.llps.conf.event.reminder.service.EventFileParser;
import fr.kevin.llps.conf.event.reminder.service.EventService;
import fr.kevin.llps.conf.event.reminder.service.TalkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventFileParser eventFileParser;
    private final TalkService talkService;
    private final EventService eventService;
    private final TalkMapper talkMapper;

    @PostMapping("/import")
    @ResponseStatus(NO_CONTENT)
    public void importEvents(@RequestParam("file") MultipartFile fileToImport) throws IOException {
        List<CsvEvent> csvEvents = eventFileParser.parse(fileToImport);

        eventService.importEvents(csvEvents);
    }

    @GetMapping("/upcoming")
    public List<TalkDto> getUpcomingEvents() {
        List<Talk> upcomingTalks = talkService.getUpcomingTalks();

        return talkMapper.mapToDto(upcomingTalks);
    }

}
