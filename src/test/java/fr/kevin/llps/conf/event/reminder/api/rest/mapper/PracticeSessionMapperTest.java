package fr.kevin.llps.conf.event.reminder.api.rest.mapper;

import fr.kevin.llps.conf.event.reminder.api.rest.dto.AttendeeDto;
import fr.kevin.llps.conf.event.reminder.api.rest.dto.PracticeSessionDto;
import fr.kevin.llps.conf.event.reminder.api.rest.dto.SpeakerDto;
import fr.kevin.llps.conf.event.reminder.domain.Attendee;
import fr.kevin.llps.conf.event.reminder.domain.PracticeSession;
import fr.kevin.llps.conf.event.reminder.domain.Speaker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

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
    void shouldMapToDto() {
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

        when(attendeeMapper.mapToDto(List.of(firstAgileAttendee, secondAgileAttendee)))
                .thenReturn(expectedAgileAttendees);

        List<AttendeeDto> expectedJeeAttendees = List.of(firstJeeAttendeeDto, secondJeeAttendeeDto);

        when(attendeeMapper.mapToDto(List.of(firstJeeAttendee, secondJeeAttendee)))
                .thenReturn(expectedJeeAttendees);

        List<PracticeSessionDto> practiceSessionDtoList = practiceSessionMapper.mapToDto(practiceSessions);

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

}
