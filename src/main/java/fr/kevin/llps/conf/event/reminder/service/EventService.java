package fr.kevin.llps.conf.event.reminder.service;

import fr.kevin.llps.conf.event.reminder.api.rest.dto.EventDto;
import fr.kevin.llps.conf.event.reminder.api.rest.mapper.TalkMapper;
import fr.kevin.llps.conf.event.reminder.csv.CsvEvent;
import fr.kevin.llps.conf.event.reminder.domain.Talk;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final TalkService talkService;
    private final TalkMapper talkMapper;

    public void importEvents(List<CsvEvent> csvEvents) {
        List<CsvEvent> csvTalks = filterByTalk(csvEvents);

        talkService.importTalks(mapToTalks(csvTalks));
    }

    private List<CsvEvent> filterByTalk(List<CsvEvent> csvEvents) {
        return csvEvents.stream()
                .filter(csvEvent -> csvEvent.getType().equals("Talk"))
                .toList();
    }

    private List<Talk> mapToTalks(List<CsvEvent> csvTalks) {
        return csvTalks.stream()
                .map(Talk::create)
                .toList();
    }

    public List<EventDto> getUpcomingEvents() {
        List<Talk> upcomingTalks = talkService.getUpcomingTalks();

        return talkMapper.mapToDto(upcomingTalks);
    }

}
