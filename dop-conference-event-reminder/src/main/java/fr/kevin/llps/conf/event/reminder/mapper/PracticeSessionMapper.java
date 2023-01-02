package fr.kevin.llps.conf.event.reminder.mapper;

import fr.kevin.llps.conf.event.reminder.api.rest.dto.AttendeeDto;
import fr.kevin.llps.conf.event.reminder.api.rest.dto.PracticeSessionDto;
import fr.kevin.llps.conf.event.reminder.api.rest.dto.SpeakerDto;
import fr.kevin.llps.conf.event.reminder.csv.CsvEvent;
import fr.kevin.llps.conf.event.reminder.domain.Attendee;
import fr.kevin.llps.conf.event.reminder.domain.PracticeSession;
import fr.kevin.llps.conf.event.reminder.domain.Speaker;
import fr.kevin.llps.conf.event.reminder.entities.AttendeeEntity;
import fr.kevin.llps.conf.event.reminder.entities.PracticeSessionAttendeeEntity;
import fr.kevin.llps.conf.event.reminder.entities.PracticeSessionEntity;
import fr.kevin.llps.conf.event.reminder.entities.SpeakerEntity;
import fr.kevin.llps.conf.event.reminder.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PracticeSessionMapper {

    private final SpeakerMapper speakerMapper;
    private final AttendeeMapper attendeeMapper;

    public List<PracticeSessionDto> mapToDtoList(List<PracticeSession> practiceSessions) {
        return practiceSessions.stream()
                .map(this::mapToDto)
                .toList();
    }

    private PracticeSessionDto mapToDto(PracticeSession practiceSession) {
        SpeakerDto speakerDto = speakerMapper.mapToDto(practiceSession.speaker());
        List<AttendeeDto> attendeeDtoList = attendeeMapper.mapToDtoList(practiceSession.attendees());

        return PracticeSessionDto.builder()
                .title(practiceSession.title())
                .description(practiceSession.description())
                .date(practiceSession.date())
                .speaker(speakerDto)
                .attendees(attendeeDtoList)
                .build();
    }

    public List<PracticeSessionEntity> mapToEntities(List<PracticeSession> practiceSessions) {
        return practiceSessions.stream()
                .map(this::mapToEntity)
                .toList();
    }

    private PracticeSessionEntity mapToEntity(PracticeSession practiceSession) {
        SpeakerEntity speakerEntity = speakerMapper.mapToEntity(practiceSession.speaker());

        PracticeSessionEntity practiceSessionEntity = new PracticeSessionEntity(practiceSession.title(), practiceSession.description(), practiceSession.date(), speakerEntity);

        practiceSessionEntity.setPracticeSessionAttendees(getPracticeSessionAttendees(practiceSession.attendees(), practiceSessionEntity));

        return practiceSessionEntity;
    }

    private List<PracticeSessionAttendeeEntity> getPracticeSessionAttendees(
            List<Attendee> attendees, PracticeSessionEntity practiceSessionEntity) {

        List<AttendeeEntity> attendeeEntities = attendeeMapper.mapToEntities(attendees);

        return attendeeEntities.stream()
                .map(attendeeEntity -> new PracticeSessionAttendeeEntity(practiceSessionEntity, attendeeEntity))
                .toList();
    }

    public List<PracticeSession> mapToPracticeSessions(List<PracticeSessionEntity> practiceSessionEntities) {
        return practiceSessionEntities.stream()
                .map(this::mapToPracticeSession)
                .toList();
    }

    private PracticeSession mapToPracticeSession(PracticeSessionEntity practiceSessionEntity) {
        Speaker speaker = speakerMapper.mapToSpeaker(practiceSessionEntity.getSpeaker());
        List<Attendee> attendees = attendeeMapper.mapToAttendees(getAttendees(practiceSessionEntity));

        return new PracticeSession(
                practiceSessionEntity.getTitle(),
                practiceSessionEntity.getDescription(),
                practiceSessionEntity.getDate(),
                speaker,
                attendees);
    }

    private List<AttendeeEntity> getAttendees(PracticeSessionEntity practiceSessionEntity) {
        return practiceSessionEntity.getPracticeSessionAttendees().stream()
                .map(PracticeSessionAttendeeEntity::getAttendee)
                .toList();
    }

    public List<PracticeSession> mapCsvEventsToPracticeSessions(List<CsvEvent> csvPracticeSessions) {
        return csvPracticeSessions.stream()
                .map(this::mapToPracticeSession)
                .toList();
    }

    private PracticeSession mapToPracticeSession(CsvEvent csvEvent) {
        return new PracticeSession(
                csvEvent.title(),
                csvEvent.description(),
                DateUtils.mapToLocalDateTime(csvEvent.date(), csvEvent.time()),
                speakerMapper.mapStrToSpeaker(csvEvent.speaker()),
                attendeeMapper.mapToAttendees(csvEvent.attendees()));
    }

}
