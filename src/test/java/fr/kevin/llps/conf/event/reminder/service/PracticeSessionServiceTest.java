package fr.kevin.llps.conf.event.reminder.service;

import fr.kevin.llps.conf.event.reminder.domain.Attendee;
import fr.kevin.llps.conf.event.reminder.domain.PracticeSession;
import fr.kevin.llps.conf.event.reminder.repository.AttendeeRepository;
import fr.kevin.llps.conf.event.reminder.repository.PracticeSessionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static fr.kevin.llps.conf.event.reminder.samples.PracticeSessionSample.practiceSessionList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class PracticeSessionServiceTest {

    @Mock
    private PracticeSessionRepository practiceSessionRepository;

    @Mock
    private AttendeeRepository attendeeRepository;

    @InjectMocks
    private PracticeSessionService practiceSessionService;

    @Test
    void shouldImportPracticeSessions() {
        List<PracticeSession> practiceSessions = practiceSessionList();
        List<Attendee> attendees = List.of(
                new Attendee("julien", "arnaud"),
                new Attendee("mickael", "dupont"),
                new Attendee("jean", "dupont"),
                new Attendee("alex", "dubois"));

        practiceSessionService.importPracticeSessions(practiceSessions);

        verify(attendeeRepository).saveAll(attendees);
        verify(practiceSessionRepository).saveAll(practiceSessions);
        verifyNoMoreInteractions(practiceSessionRepository);
    }

}
