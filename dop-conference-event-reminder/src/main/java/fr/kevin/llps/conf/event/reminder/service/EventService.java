package fr.kevin.llps.conf.event.reminder.service;

import fr.kevin.llps.conf.event.reminder.api.rest.dto.EventDto;
import fr.kevin.llps.conf.event.reminder.csv.CsvEvent;
import fr.kevin.llps.conf.event.reminder.domain.*;
import fr.kevin.llps.conf.event.reminder.exception.EventExportException;
import fr.kevin.llps.conf.event.reminder.mapper.BBLMapper;
import fr.kevin.llps.conf.event.reminder.mapper.PracticeSessionMapper;
import fr.kevin.llps.conf.event.reminder.mapper.TalkMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

        List<Talk> talks = talkMapper.mapCsvEventsToTalks(csvTalks);
        List<BBL> bblList = bblMapper.mapCsvEventsToBBLs(csvBBLs);
        List<PracticeSession> practiceSessions = practiceSessionMapper.mapCsvEventsToPracticeSessions(csvPracticeSessions);

        talkService.importTalks(talkMapper.mapToEntities(talks));
        bblService.importBBLs(bblMapper.mapToEntities(bblList));
        practiceSessionService.importPracticeSessions(practiceSessionMapper.mapToEntities(practiceSessions));
    }

    private List<CsvEvent> filterByEventType(List<CsvEvent> csvEvents, String eventType) {
        return csvEvents.stream()
                .filter(csvEvent -> csvEvent.type().equals(eventType))
                .toList();
    }

    public List<EventDto> getUpcomingEvents() {
        List<EventDto> eventDtoList = new ArrayList<>();

        List<Talk> upcomingTalks = talkService.getUpcomingTalks();
        List<BBL> upcomingBBLs = bblService.getUpcomingBBLs();
        List<PracticeSession> upcomingPracticeSessions = practiceSessionService.getUpcomingPracticeSessions();

        eventDtoList.addAll(talkMapper.mapToDtoList(upcomingTalks));
        eventDtoList.addAll(bblMapper.mapToDtoList(upcomingBBLs));
        eventDtoList.addAll(practiceSessionMapper.mapToDtoList(upcomingPracticeSessions));

        return eventDtoList;
    }

    public byte[] exportEvents() {
        CSVFormat csvFormat = CSVFormat.newFormat(DELIMITER).builder()
                .setHeader(HEADERS)
                .setRecordSeparator(LF)
                .build();

        List<Event> events = getAllEvents();

        List<String[]> csvColumns = events.stream()
                .map(this::getCsvColumns)
                .toList();

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(byteArrayOutputStream), csvFormat)) {

            csvPrinter.printRecords(csvColumns);

            csvPrinter.flush();

            return byteArrayOutputStream.toByteArray();
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

    private String[] getCsvColumns(Event event) {
        return switch (event) {
            case Talk(String title,String description,LocalDateTime date,Speaker speaker)
                    -> getTalkCsvColumns(title, description, date, speaker);
            case BBL(String title,String description,LocalDateTime date,Speaker speaker,String company)
                    -> getBBLCsvColumns(title, description, date, speaker, company);
            case PracticeSession(String title,String description,LocalDateTime date,Speaker speaker,List<Attendee> attendees)
                    -> getPracticeSessionCsvColumns(title, description, date, speaker, attendees);
        };
    }

    private String[] getTalkCsvColumns(String title, String description, LocalDateTime date, Speaker speaker) {
        return new String[]{
                title,
                EventType.TALK,
                description,
                date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                date.format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                String.format("%s %s", speaker.firstname(), speaker.lastname()),
                "", ""};
    }

    private String[] getBBLCsvColumns(String title, String description, LocalDateTime date, Speaker speaker, String company) {
        return new String[]{
                title,
                EventType.BBL,
                description,
                date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                date.format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                String.format("%s %s", speaker.firstname(), speaker.lastname()),
                "",
                company};
    }

    private String[] getPracticeSessionCsvColumns(String title, String description, LocalDateTime date, Speaker speaker, List<Attendee> attendees) {
        return new String[]{
                title,
                EventType.PRACTICE_SESSION,
                description,
                date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                date.format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                String.format("%s %s", speaker.firstname(), speaker.lastname()),
                getAttendeesCsvColumn(attendees),
                ""};
    }

    private String getAttendeesCsvColumn(List<Attendee> attendees) {
        return attendees.stream()
                .map(attendee -> String.format("%s %s", attendee.firstname(), attendee.lastname()))
                .sorted()
                .collect(Collectors.joining(","));
    }

}
