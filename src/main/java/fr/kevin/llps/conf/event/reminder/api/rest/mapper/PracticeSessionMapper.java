package fr.kevin.llps.conf.event.reminder.api.rest.mapper;

import fr.kevin.llps.conf.event.reminder.api.rest.dto.AttendeeDto;
import fr.kevin.llps.conf.event.reminder.api.rest.dto.PracticeSessionDto;
import fr.kevin.llps.conf.event.reminder.api.rest.dto.SpeakerDto;
import fr.kevin.llps.conf.event.reminder.domain.Attendee;
import fr.kevin.llps.conf.event.reminder.domain.PracticeSession;
import fr.kevin.llps.conf.event.reminder.domain.PracticeSessionAttendee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PracticeSessionMapper {

    private final SpeakerMapper speakerMapper;
    private final AttendeeMapper attendeeMapper;

    public List<PracticeSessionDto> mapToDto(List<PracticeSession> practiceSessions) {
        return practiceSessions.stream()
                .map(this::mapToDto)
                .toList();
    }

    private PracticeSessionDto mapToDto(PracticeSession practiceSession) {
        SpeakerDto speakerDto = speakerMapper.mapToDto(practiceSession.getSpeaker());
        List<AttendeeDto> attendeeDtoList = attendeeMapper.mapToDto(getAttendees(practiceSession));

        return PracticeSessionDto.builder()
                .title(practiceSession.getTitle())
                .description(practiceSession.getDescription())
                .date(practiceSession.getDate())
                .speaker(speakerDto)
                .attendees(attendeeDtoList)
                .build();
    }

    private List<Attendee> getAttendees(PracticeSession practiceSession) {
        return practiceSession.getPracticeSessionAttendees().stream()
                .map(PracticeSessionAttendee::getAttendee)
                .toList();
    }

}
