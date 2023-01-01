package fr.kevin.llps.conf.event.reminder.service;

import fr.kevin.llps.conf.event.reminder.domain.Attendee;
import fr.kevin.llps.conf.event.reminder.domain.PracticeSession;
import fr.kevin.llps.conf.event.reminder.repository.AttendeeRepository;
import fr.kevin.llps.conf.event.reminder.repository.PracticeSessionRepository;
import fr.kevin.llps.conf.event.reminder.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static fr.kevin.llps.conf.event.reminder.samples.PracticeSessionSample.practiceSessionList;
import static fr.kevin.llps.conf.event.reminder.utils.TestUtils.DATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PracticeSessionServiceTest {

    @Mock
    private PracticeSessionRepository practiceSessionRepository;

    @Mock
    private AttendeeRepository attendeeRepository;

    @Mock
    private DateUtils dateUtils;

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

    @Test
    void shouldGetUpcomingPracticeSessions() {
        List<PracticeSession> expectedPracticeSessions = practiceSessionList();

        when(dateUtils.getCurrentDate()).thenReturn(DATE);
        when(practiceSessionRepository.findByDateLaterThan(DATE)).thenReturn(expectedPracticeSessions);

        List<PracticeSession> upcomingPracticeSessions = practiceSessionService.getUpcomingPracticeSessions();

        assertThat(upcomingPracticeSessions).containsExactlyInAnyOrderElementsOf(expectedPracticeSessions);

        verifyNoMoreInteractions(dateUtils, practiceSessionRepository);
    }

    @Test
    void shouldGetAll() {
        List<PracticeSession> expectedPracticeSessions = practiceSessionList();

        when(practiceSessionRepository.findAllOrderedByDate()).thenReturn(expectedPracticeSessions);

        List<PracticeSession> practiceSessions = practiceSessionService.getAll();

        assertThat(practiceSessions).containsExactlyInAnyOrderElementsOf(expectedPracticeSessions);

        verifyNoMoreInteractions(practiceSessionRepository);
    }

}
