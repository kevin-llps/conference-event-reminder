package fr.kevin.llps.conf.event.reminder.api.rest.mapper;

import fr.kevin.llps.conf.event.reminder.api.rest.dto.AttendeeDto;
import fr.kevin.llps.conf.event.reminder.domain.Attendee;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AttendeeMapper {

    public List<AttendeeDto> mapToDtoList(List<Attendee> attendees) {
        return attendees.stream()
                .map(this::mapToDto)
                .toList();
    }

    private AttendeeDto mapToDto(Attendee attendee) {
        return AttendeeDto.builder()
                .firstname(attendee.getFirstname())
                .lastname(attendee.getLastname())
                .build();
    }

}
