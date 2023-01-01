package fr.kevin.llps.conf.event.reminder.mapper;

import fr.kevin.llps.conf.event.reminder.api.rest.dto.AttendeeDto;
import fr.kevin.llps.conf.event.reminder.domain.Attendee;
import fr.kevin.llps.conf.event.reminder.entities.AttendeeEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
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
                .firstname(attendee.firstname())
                .lastname(attendee.lastname())
                .build();
    }

    public List<AttendeeEntity> mapToEntities(List<Attendee> attendees) {
        return attendees.stream()
                .map(this::mapToEntity)
                .toList();
    }

    private AttendeeEntity mapToEntity(Attendee attendee) {
        return new AttendeeEntity(attendee.firstname(), attendee.lastname());
    }

    public List<Attendee> mapToAttendees(List<AttendeeEntity> attendeeEntities) {
        return attendeeEntities.stream()
                .map(this::mapToAttendee)
                .toList();
    }

    private Attendee mapToAttendee(AttendeeEntity attendeeEntity) {
        return new Attendee(attendeeEntity.getFirstname(), attendeeEntity.getLastname());
    }

    public List<Attendee> mapToAttendees(String attendees) {
        return Arrays.stream(attendees.split(","))
                .map(this::mapToAttendee)
                .toList();
    }

    private Attendee mapToAttendee(String attendee) {
        String[] attendeeParts = attendee.split(" ", 2);

        return new Attendee(attendeeParts[0], attendeeParts[1]);
    }

}
