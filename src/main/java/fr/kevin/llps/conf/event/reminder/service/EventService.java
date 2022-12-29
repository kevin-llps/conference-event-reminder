package fr.kevin.llps.conf.event.reminder.service;

import fr.kevin.llps.conf.event.reminder.api.rest.dto.EventDto;
import fr.kevin.llps.conf.event.reminder.api.rest.mapper.BBLMapper;
import fr.kevin.llps.conf.event.reminder.api.rest.mapper.PracticeSessionMapper;
import fr.kevin.llps.conf.event.reminder.api.rest.mapper.TalkMapper;
import fr.kevin.llps.conf.event.reminder.csv.CsvEvent;
import fr.kevin.llps.conf.event.reminder.domain.BBL;
import fr.kevin.llps.conf.event.reminder.domain.EventType;
import fr.kevin.llps.conf.event.reminder.domain.PracticeSession;
import fr.kevin.llps.conf.event.reminder.domain.Talk;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final TalkService talkService;
    private final BBLService bblService;
    private final PracticeSessionService practiceSessionService;
    private final TalkMapper talkMapper;
    private final BBLMapper bblMapper;
    private final PracticeSessionMapper practiceSessionMapper;

    public void importEvents(List<CsvEvent> csvEvents) {
        List<CsvEvent> csvTalks = filterByEventType(csvEvents, EventType.TALK);
        List<CsvEvent> csvBBLs = filterByEventType(csvEvents, EventType.BBL);
        List<CsvEvent> csvPracticeSessions = filterByEventType(csvEvents, EventType.PRACTICE_SESSION);

        talkService.importTalks(mapToTalks(csvTalks));
        bblService.importBBLs(mapToBBLs(csvBBLs));
        practiceSessionService.importPracticeSessions(mapToPracticeSessions(csvPracticeSessions));
    }

    private List<CsvEvent> filterByEventType(List<CsvEvent> csvEvents, String eventType) {
        return csvEvents.stream()
                .filter(csvEvent -> csvEvent.getType().equals(eventType))
                .toList();
    }

    private List<Talk> mapToTalks(List<CsvEvent> csvTalks) {
        return csvTalks.stream()
                .map(Talk::create)
                .toList();
    }

    private List<BBL> mapToBBLs(List<CsvEvent> csvBBLs) {
        return csvBBLs.stream()
                .map(BBL::create)
                .toList();
    }

    private List<PracticeSession> mapToPracticeSessions(List<CsvEvent> csvPracticeSessions) {
        return csvPracticeSessions.stream()
                .map(PracticeSession::create)
                .toList();
    }

    public List<EventDto> getUpcomingEvents() {
        List<EventDto> eventDtoList = new ArrayList<>();

        List<Talk> upcomingTalks = talkService.getUpcomingTalks();
        List<BBL> upcomingBBLs = bblService.getUpcomingBBLs();
        List<PracticeSession> upcomingPracticeSessions = practiceSessionService.getUpcomingPracticeSessions();

        eventDtoList.addAll(talkMapper.mapToDto(upcomingTalks));
        eventDtoList.addAll(bblMapper.mapToDto(upcomingBBLs));
        eventDtoList.addAll(practiceSessionMapper.mapToDto(upcomingPracticeSessions));

        return eventDtoList;
    }

}
