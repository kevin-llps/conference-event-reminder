package fr.kevin.llps.conf.event.reminder.mapper;

import fr.kevin.llps.conf.event.reminder.api.rest.dto.AttendeeDto;
import fr.kevin.llps.conf.event.reminder.api.rest.dto.PracticeSessionDto;
import fr.kevin.llps.conf.event.reminder.api.rest.dto.SpeakerDto;
import fr.kevin.llps.conf.event.reminder.csv.CsvEvent;
import fr.kevin.llps.conf.event.reminder.domain.Attendee;
import fr.kevin.llps.conf.event.reminder.domain.PracticeSession;
import fr.kevin.llps.conf.event.reminder.domain.Speaker;
import fr.kevin.llps.conf.event.reminder.entities.AttendeeEntity;
import fr.kevin.llps.conf.event.reminder.entities.PracticeSessionEntity;
import fr.kevin.llps.conf.event.reminder.entities.SpeakerEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static fr.kevin.llps.conf.event.reminder.samples.CsvEventSample.csvPracticeSessionEvents;
import static fr.kevin.llps.conf.event.reminder.samples.PracticeSessionEntitySample.practiceSessionEntities;
import static fr.kevin.llps.conf.event.reminder.samples.PracticeSessionSample.practiceSessionList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PracticeSessionMapperTest {

    @Mock
    private AttendeeMapper attendeeMapper;

    @Mock
    private SpeakerMapper speakerMapper;

    @InjectMocks
    private PracticeSessionMapper practiceSessionMapper;

    @Test
    void shouldMapToDtoList() {
        List<PracticeSession> practiceSessions = practiceSessionList();

        Speaker speakerKevLps = new Speaker("kevin", "llps");
        Speaker speakerChrisArr = new Speaker("chris", "arr");

        SpeakerDto speakerKevLpsDto = SpeakerDto.builder()
                .firstname("kevin")
                .lastname("llps")
                .build();

        SpeakerDto speakerChrisArrDto = SpeakerDto.builder()
                .firstname("chris")
                .lastname("arr")
                .build();

        Attendee firstJeeAttendee = new Attendee("julien", "arnaud");
        Attendee secondJeeAttendee = new Attendee("mickael", "dupont");

        AttendeeDto firstJeeAttendeeDto = AttendeeDto.builder()
                .firstname("julien")
                .lastname("arnaud")
                .build();

        AttendeeDto secondJeeAttendeeDto = AttendeeDto.builder()
                .firstname("mickael")
                .lastname("dupont")
                .build();

        Attendee firstAgileAttendee = new Attendee("jean", "dupont");
        Attendee secondAgileAttendee = new Attendee("alex", "dubois");

        AttendeeDto firstAgileAttendeeDto = AttendeeDto.builder()
                .firstname("jean")
                .lastname("dupont")
                .build();

        AttendeeDto secondAgileAttendeeDto = AttendeeDto.builder()
                .firstname("alex")
                .lastname("dubois")
                .build();

        when(speakerMapper.mapToDto(speakerKevLps)).thenReturn(speakerKevLpsDto);
        when(speakerMapper.mapToDto(speakerChrisArr)).thenReturn(speakerChrisArrDto);

        List<AttendeeDto> expectedAgileAttendees = List.of(firstAgileAttendeeDto, secondAgileAttendeeDto);

        when(attendeeMapper.mapToDtoList(List.of(firstAgileAttendee, secondAgileAttendee)))
                .thenReturn(expectedAgileAttendees);

        List<AttendeeDto> expectedJeeAttendees = List.of(firstJeeAttendeeDto, secondJeeAttendeeDto);

        when(attendeeMapper.mapToDtoList(List.of(firstJeeAttendee, secondJeeAttendee)))
                .thenReturn(expectedJeeAttendees);

        List<PracticeSessionDto> practiceSessionDtoList = practiceSessionMapper.mapToDtoList(practiceSessions);

        assertThat(practiceSessionDtoList).isNotNull()
                .hasSize(2)
                .extracting("title", "description", "date", "speaker.firstname", "speaker.lastname", "attendees")
                .containsExactlyInAnyOrder(
                        tuple("Agile",
                                "Présentation et pratique de la méthode agile",
                                LocalDateTime.of(2022, 9, 20, 14, 30, 0),
                                "chris", "arr",
                                expectedAgileAttendees),
                        tuple("JEE",
                                "Session pratique JEE",
                                LocalDateTime.of(2023, 4, 11, 19, 0, 0),
                                "kevin", "llps",
                                expectedJeeAttendees));
    }

    @Test
    void shouldMapToEntities() {
        List<PracticeSession> practiceSessions = practiceSessionList();

        Speaker speakerKevLps = new Speaker("kevin", "llps");
        Speaker speakerChrisArr = new Speaker("chris", "arr");

        SpeakerEntity speakerKevLpsEntity = new SpeakerEntity("kevin", "llps");
        SpeakerEntity speakerChrisArrEntity = new SpeakerEntity("chris", "arr");

        Attendee firstJeeAttendee = new Attendee("julien", "arnaud");
        Attendee secondJeeAttendee = new Attendee("mickael", "dupont");

        AttendeeEntity firstJeeAttendeeEntity = new AttendeeEntity("julien", "arnaud");
        AttendeeEntity secondJeeAttendeeEntity = new AttendeeEntity("mickael", "dupont");

        Attendee firstAgileAttendee = new Attendee("jean", "dupont");
        Attendee secondAgileAttendee = new Attendee("alex", "dubois");

        AttendeeEntity firstAgileAttendeeEntity = new AttendeeEntity("jean", "dupont");
        AttendeeEntity secondAgileAttendeeEntity = new AttendeeEntity("alex", "dubois");

        when(speakerMapper.mapToEntity(speakerKevLps)).thenReturn(speakerKevLpsEntity);
        when(speakerMapper.mapToEntity(speakerChrisArr)).thenReturn(speakerChrisArrEntity);

        List<AttendeeEntity> expectedAgileAttendees = List.of(firstAgileAttendeeEntity, secondAgileAttendeeEntity);

        when(attendeeMapper.mapToEntities(List.of(firstAgileAttendee, secondAgileAttendee))).thenReturn(expectedAgileAttendees);

        List<AttendeeEntity> expectedJeeAttendees = List.of(firstJeeAttendeeEntity, secondJeeAttendeeEntity);

        when(attendeeMapper.mapToEntities(List.of(firstJeeAttendee, secondJeeAttendee))).thenReturn(expectedJeeAttendees);

        List<PracticeSessionEntity> practiceSessionEntities = practiceSessionMapper.mapToEntities(practiceSessions);

        assertThat(practiceSessionEntities).isNotNull()
                .hasSize(2)
                .extracting("title", "description", "date", "speaker.firstname", "speaker.lastname")
                .containsExactlyInAnyOrder(
                        tuple("Agile",
                                "Présentation et pratique de la méthode agile",
                                LocalDateTime.of(2022, 9, 20, 14, 30, 0),
                                "chris", "arr"),
                        tuple("JEE",
                                "Session pratique JEE",
                                LocalDateTime.of(2023, 4, 11, 19, 0, 0),
                                "kevin", "llps"));

        Assertions.assertThat(practiceSessionEntities.get(0).getPracticeSessionAttendees()).isNotNull()
                .hasSize(2)
                .extracting("attendee.firstname", "attendee.lastname")
                .containsExactlyInAnyOrder(
                        tuple("julien", "arnaud"),
                        tuple("mickael", "dupont"));

        Assertions.assertThat(practiceSessionEntities.get(1).getPracticeSessionAttendees()).isNotNull()
                .hasSize(2)
                .extracting("attendee.firstname", "attendee.lastname")
                .containsExactlyInAnyOrder(
                        tuple("jean", "dupont"),
                        tuple("alex", "dubois"));
    }

    @Test
    void shouldMapToPracticeSessions() {
        List<PracticeSessionEntity> practiceSessionEntities = practiceSessionEntities();

        SpeakerEntity speakerKevLpsEntity = new SpeakerEntity("kevin", "llps");
        SpeakerEntity speakerChrisArrEntity = new SpeakerEntity("chris", "arr");

        Speaker speakerKevLps = new Speaker("kevin", "llps");
        Speaker speakerChrisArr = new Speaker("chris", "arr");

        AttendeeEntity firstJeeAttendeeEntity = new AttendeeEntity("julien", "arnaud");
        AttendeeEntity secondJeeAttendeeEntity = new AttendeeEntity("mickael", "dupont");

        AttendeeEntity firstAgileAttendeeEntity = new AttendeeEntity("jean", "dupont");
        AttendeeEntity secondAgileAttendeeEntity = new AttendeeEntity("alex", "dubois");

        Attendee firstJeeAttendee = new Attendee("julien", "arnaud");
        Attendee secondJeeAttendee = new Attendee("mickael", "dupont");

        Attendee firstAgileAttendee = new Attendee("jean", "dupont");
        Attendee secondAgileAttendee = new Attendee("alex", "dubois");

        when(speakerMapper.mapToSpeaker(speakerKevLpsEntity)).thenReturn(speakerKevLps);
        when(speakerMapper.mapToSpeaker(speakerChrisArrEntity)).thenReturn(speakerChrisArr);

        List<Attendee> expectedAgileAttendees = List.of(firstAgileAttendee, secondAgileAttendee);

        when(attendeeMapper.mapToAttendees(List.of(firstAgileAttendeeEntity, secondAgileAttendeeEntity))).thenReturn(expectedAgileAttendees);

        List<Attendee> expectedJeeAttendees = List.of(firstJeeAttendee, secondJeeAttendee);

        when(attendeeMapper.mapToAttendees(List.of(firstJeeAttendeeEntity, secondJeeAttendeeEntity))).thenReturn(expectedJeeAttendees);

        List<PracticeSession> practiceSessions = practiceSessionMapper.mapToPracticeSessions(practiceSessionEntities);

        assertThat(practiceSessions).isNotNull()
                .hasSize(2)
                .extracting("title", "description", "date", "speaker.firstname", "speaker.lastname")
                .containsExactlyInAnyOrder(
                        tuple("Agile",
                                "Présentation et pratique de la méthode agile",
                                LocalDateTime.of(2022, 9, 20, 14, 30, 0),
                                "chris", "arr"),
                        tuple("JEE",
                                "Session pratique JEE",
                                LocalDateTime.of(2023, 4, 11, 19, 0, 0),
                                "kevin", "llps"));

        assertThat(practiceSessions.get(0).attendees()).isNotNull()
                .hasSize(2)
                .extracting("firstname", "lastname")
                .containsExactlyInAnyOrder(
                        tuple("julien", "arnaud"),
                        tuple("mickael", "dupont"));

        assertThat(practiceSessions.get(1).attendees()).isNotNull()
                .hasSize(2)
                .extracting("firstname", "lastname")
                .containsExactlyInAnyOrder(
                        tuple("jean", "dupont"),
                        tuple("alex", "dubois"));
    }

    @Test
    void shouldMapCsvEventsToPracticeSessions() {
        List<CsvEvent> csvEvents = csvPracticeSessionEvents();

        String strSpeakerKevLps = "kevin llps";
        String strSpeakerChrisArr = "chris arr";

        Speaker speakerKevLps = new Speaker("kevin", "llps");
        Speaker speakerChrisArr = new Speaker("chris", "arr");

        String jeeAttendees = "julien arnaud,mickael dupont";
        String agileAttendees = "jean dupont,alex dubois";

        Attendee firstJeeAttendee = new Attendee("julien", "arnaud");
        Attendee secondJeeAttendee = new Attendee("mickael", "dupont");

        Attendee firstAgileAttendee = new Attendee("jean", "dupont");
        Attendee secondAgileAttendee = new Attendee("alex", "dubois");

        when(speakerMapper.mapStrToSpeaker(strSpeakerKevLps)).thenReturn(speakerKevLps);
        when(speakerMapper.mapStrToSpeaker(strSpeakerChrisArr)).thenReturn(speakerChrisArr);

        when(attendeeMapper.mapToAttendees(jeeAttendees)).thenReturn(List.of(firstJeeAttendee, secondJeeAttendee));
        when(attendeeMapper.mapToAttendees(agileAttendees)).thenReturn(List.of(firstAgileAttendee, secondAgileAttendee));

        List<PracticeSession> practiceSessions = practiceSessionMapper.mapCsvEventsToPracticeSessions(csvEvents);

        assertThat(practiceSessions).isNotNull()
                .hasSize(2)
                .extracting("title", "description", "date", "speaker.firstname", "speaker.lastname")
                .containsExactlyInAnyOrder(
                        tuple("Agile",
                                "Présentation et pratique de la méthode agile",
                                LocalDateTime.of(2022, 9, 20, 14, 30, 0),
                                "chris", "arr"),
                        tuple("JEE",
                                "Session pratique JEE",
                                LocalDateTime.of(2023, 4, 11, 19, 0, 0),
                                "kevin", "llps"));

        assertThat(practiceSessions.get(0).attendees()).isNotNull()
                .hasSize(2)
                .extracting("firstname", "lastname")
                .containsExactlyInAnyOrder(
                        tuple("julien", "arnaud"),
                        tuple("mickael", "dupont"));

        assertThat(practiceSessions.get(1).attendees()).isNotNull()
                .hasSize(2)
                .extracting("firstname", "lastname")
                .containsExactlyInAnyOrder(
                        tuple("jean", "dupont"),
                        tuple("alex", "dubois"));
    }

}
