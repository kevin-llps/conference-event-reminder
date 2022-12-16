package fr.kevin.llps.conf.event.reminder.api.rest;

import fr.kevin.llps.conf.event.reminder.api.rest.dto.TalkDto;
import fr.kevin.llps.conf.event.reminder.api.rest.mapper.TalkMapper;
import fr.kevin.llps.conf.event.reminder.domain.Talk;
import fr.kevin.llps.conf.event.reminder.service.TalkFileParser;
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

    private final TalkFileParser talkFileParser;
    private final TalkService talkService;
    private final TalkMapper talkMapper;

    @PostMapping("/import")
    @ResponseStatus(NO_CONTENT)
    public void importEvents(@RequestParam("file") MultipartFile fileToImport) throws IOException {
        List<Talk> talks = talkFileParser.parse(fileToImport);

        talkService.importTalks(talks);
    }

    @GetMapping("/upcoming")
    public List<TalkDto> getUpcomingEvents() {
        List<Talk> upcomingTalks = talkService.getUpcomingTalks();

        return talkMapper.mapToDto(upcomingTalks);
    }

}
