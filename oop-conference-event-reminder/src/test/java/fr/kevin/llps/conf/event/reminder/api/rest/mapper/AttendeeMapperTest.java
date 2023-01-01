package fr.kevin.llps.conf.event.reminder.api.rest.mapper;

import fr.kevin.llps.conf.event.reminder.api.rest.dto.AttendeeDto;
import fr.kevin.llps.conf.event.reminder.domain.Attendee;
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

        assertThat(attendeeDtoList).isNotNull()
                .hasSize(2)
                .containsExactlyInAnyOrderElementsOf(List.of(firstAttendeeDto, secondAttendeeDto));
    }

}
