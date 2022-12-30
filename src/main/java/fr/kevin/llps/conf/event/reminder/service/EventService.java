package fr.kevin.llps.conf.event.reminder.service;

import fr.kevin.llps.conf.event.reminder.api.rest.dto.EventDto;
import fr.kevin.llps.conf.event.reminder.api.rest.mapper.BBLMapper;
import fr.kevin.llps.conf.event.reminder.api.rest.mapper.PracticeSessionMapper;
import fr.kevin.llps.conf.event.reminder.api.rest.mapper.TalkMapper;
import fr.kevin.llps.conf.event.reminder.csv.CsvEvent;
import fr.kevin.llps.conf.event.reminder.domain.*;
import fr.kevin.llps.conf.event.reminder.exception.EventExportException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static fr.kevin.llps.conf.event.reminder.csv.CsvProperties.DELIMITER;
import static fr.kevin.llps.conf.event.reminder.csv.CsvProperties.HEADERS;
import static org.apache.commons.lang3.CharUtils.LF;

@Slf4j
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
                .filter(csvEvent -> csvEvent.type().equals(eventType))
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

    public ByteArrayInputStream exportEvents() {
        CSVFormat csvFormat = CSVFormat.newFormat(DELIMITER).builder()
                .setHeader(HEADERS)
                .setRecordSeparator(LF)
                .build();

        List<Event> events = getAllEvents();

        List<String[]> csvColumns = events.stream()
                .map(Event::getCsvColumns)
                .toList();

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(byteArrayOutputStream), csvFormat)) {

            csvPrinter.printRecords(csvColumns);

            csvPrinter.flush();

            return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            log.error("Une erreur est survenue lors de l'export", e);
            throw new EventExportException(e);
        }
    }

    private List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();

        events.addAll(talkService.getAll());
        events.addAll(bblService.getAll());
        events.addAll(practiceSessionService.getAll());

        return events;
    }

}
