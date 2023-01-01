package fr.kevin.llps.conf.event.reminder.mapper;

import fr.kevin.llps.conf.event.reminder.api.rest.dto.AttendeeDto;
import fr.kevin.llps.conf.event.reminder.domain.Attendee;
import fr.kevin.llps.conf.event.reminder.entities.AttendeeEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Component
class AttendeeMapperTest {

    @Test
    void shouldMapToDtoList() {
        Attendee firstAttendee = new Attendee("julien", "arnaud");
        Attendee secondAttendee = new Attendee("mickael", "dupont");

        AttendeeDto firstAttendeeDto = AttendeeDto.builder()
                .firstname("julien")
                .lastname("arnaud")
                .build();

        AttendeeDto secondAttendeeDto = AttendeeDto.builder()
                .firstname("mickael")
                .lastname("dupont")
                .build();

        AttendeeMapper attendeeMapper = new AttendeeMapper();

        List<AttendeeDto> attendeeDtoList = attendeeMapper.mapToDtoList(List.of(firstAttendee, secondAttendee));

        Assertions.assertThat(attendeeDtoList).isNotNull()
                .hasSize(2)
                .containsExactlyInAnyOrderElementsOf(List.of(firstAttendeeDto, secondAttendeeDto));
    }

    @Test
    void shouldMapToEntities() {
        Attendee firstAttendee = new Attendee("julien", "arnaud");
        Attendee secondAttendee = new Attendee("mickael", "dupont");

        AttendeeEntity firstAttendeeEntity = new AttendeeEntity("julien", "arnaud");
        AttendeeEntity secondAttendeeEntity = new AttendeeEntity("mickael", "dupont");

        AttendeeMapper attendeeMapper = new AttendeeMapper();

        List<AttendeeEntity> attendeeEntities = attendeeMapper.mapToEntities(List.of(firstAttendee, secondAttendee));

        assertThat(attendeeEntities).isNotNull()
                .hasSize(2)
                .containsExactlyInAnyOrderElementsOf(List.of(firstAttendeeEntity, secondAttendeeEntity));
    }

    @Test
    void shouldMapToAttendees() {
        AttendeeEntity firstAttendeeEntity = new AttendeeEntity("julien", "arnaud");
        AttendeeEntity secondAttendeeEntity = new AttendeeEntity("mickael", "dupont");

        Attendee firstAttendee = new Attendee("julien", "arnaud");
        Attendee secondAttendee = new Attendee("mickael", "dupont");

        AttendeeMapper attendeeMapper = new AttendeeMapper();

        List<Attendee> attendees = attendeeMapper.mapToAttendees(List.of(firstAttendeeEntity, secondAttendeeEntity));

        assertThat(attendees).isNotNull()
                .hasSize(2)
                .containsExactlyInAnyOrderElementsOf(List.of(firstAttendee, secondAttendee));
    }

    @Test
    void shouldStrMapToAttendees() {
        String strAttendees = "julien arnaud,mickael dupont";

        Attendee firstAttendee = new Attendee("julien", "arnaud");
        Attendee secondAttendee = new Attendee("mickael", "dupont");

        AttendeeMapper attendeeMapper = new AttendeeMapper();

        List<Attendee> attendees = attendeeMapper.mapToAttendees(strAttendees);

        assertThat(attendees).isNotNull()
                .hasSize(2)
                .containsExactlyInAnyOrderElementsOf(List.of(firstAttendee, secondAttendee));
    }

}
