package fr.kevin.llps.conf.event.reminder.service;

import fr.kevin.llps.conf.event.reminder.domain.Attendee;
import fr.kevin.llps.conf.event.reminder.domain.PracticeSession;
import fr.kevin.llps.conf.event.reminder.domain.PracticeSessionAttendee;
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

    public void importPracticeSessions(List<PracticeSession> practiceSessions) {
        attendeeRepository.saveAll(getAttendees(practiceSessions));
        practiceSessionRepository.saveAll(practiceSessions);
    }

    private static List<Attendee> getAttendees(List<PracticeSession> practiceSessions) {
        return practiceSessions.stream()
                .map(PracticeSession::getPracticeSessionAttendees)
                .flatMap(Collection::stream)
                .map(PracticeSessionAttendee::getAttendee)
                .toList();
    }

    public List<PracticeSession> getUpcomingPracticeSessions() {
        LocalDateTime currentDate = dateUtils.getCurrentDate();

        return practiceSessionRepository.findByDateLaterThan(currentDate);
    }

    public List<PracticeSession> getAll() {
        return practiceSessionRepository.findAllOrderedByDate();
    }
}
