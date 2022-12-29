package fr.kevin.llps.conf.event.reminder.service;

import fr.kevin.llps.conf.event.reminder.domain.Attendee;
import fr.kevin.llps.conf.event.reminder.domain.PracticeSession;
import fr.kevin.llps.conf.event.reminder.domain.PracticeSessionAttendee;
import fr.kevin.llps.conf.event.reminder.repository.AttendeeRepository;
import fr.kevin.llps.conf.event.reminder.repository.PracticeSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PracticeSessionService {

    private final PracticeSessionRepository practiceSessionRepository;
    private final AttendeeRepository attendeeRepository;

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

}
