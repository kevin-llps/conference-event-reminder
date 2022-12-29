package fr.kevin.llps.conf.event.reminder.api.rest;

import fr.kevin.llps.conf.event.reminder.api.rest.dto.EventDto;
import fr.kevin.llps.conf.event.reminder.csv.CsvEvent;
import fr.kevin.llps.conf.event.reminder.service.EventFileParser;
import fr.kevin.llps.conf.event.reminder.service.EventService;
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
    private final EventService eventService;

    @PostMapping("/import")
    @ResponseStatus(NO_CONTENT)
    public void importEvents(@RequestParam("file") MultipartFile fileToImport) throws IOException {
        List<CsvEvent> csvEvents = eventFileParser.parse(fileToImport);

        eventService.importEvents(csvEvents);
    }

    @GetMapping("/upcoming")
    public List<EventDto> getUpcomingEvents() {
        return eventService.getUpcomingEvents();
    }

}
