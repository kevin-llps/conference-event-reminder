package fr.kevin.llps.conf.event.reminder.service;

import fr.kevin.llps.conf.event.reminder.domain.PracticeSession;
import fr.kevin.llps.conf.event.reminder.entities.AttendeeEntity;
import fr.kevin.llps.conf.event.reminder.entities.PracticeSessionAttendeeEntity;
import fr.kevin.llps.conf.event.reminder.entities.PracticeSessionEntity;
import fr.kevin.llps.conf.event.reminder.mapper.PracticeSessionMapper;
import fr.kevin.llps.conf.event.reminder.repository.AttendeeRepository;
import fr.kevin.llps.conf.event.reminder.repository.PracticeSessionRepository;
import fr.kevin.llps.conf.event.reminder.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PracticeSessionService {

    private final PracticeSessionRepository practiceSessionRepository;
    private final AttendeeRepository attendeeRepository;
    private final DateUtils dateUtils;
    private final PracticeSessionMapper practiceSessionMapper;

    public void importPracticeSessions(List<PracticeSessionEntity> practiceSessions) {
        attendeeRepository.saveAll(getAttendees(practiceSessions));
        practiceSessionRepository.saveAll(practiceSessions);
    }

    private static List<AttendeeEntity> getAttendees(List<PracticeSessionEntity> practiceSessions) {
        return practiceSessions.stream()
                .map(PracticeSessionEntity::getPracticeSessionAttendees)
                .flatMap(Collection::stream)
                .map(PracticeSessionAttendeeEntity::getAttendee)
                .toList();
    }

    public List<PracticeSession> getUpcomingPracticeSessions() {
        LocalDateTime currentDate = dateUtils.getCurrentDate();

        List<PracticeSessionEntity> practiceSessionEntities = practiceSessionRepository.findByDateLaterThan(currentDate);

        return practiceSessionMapper.mapToPracticeSessions(practiceSessionEntities);
    }

    public List<PracticeSession> getAll() {
        List<PracticeSessionEntity> practiceSessionEntities = practiceSessionRepository.findAllOrderedByDate();

        return practiceSessionMapper.mapToPracticeSessions(practiceSessionEntities);
    }
}
