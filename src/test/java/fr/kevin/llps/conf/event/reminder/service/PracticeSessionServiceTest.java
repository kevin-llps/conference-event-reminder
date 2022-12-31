package fr.kevin.llps.conf.event.reminder.service;

import fr.kevin.llps.conf.event.reminder.domain.PracticeSession;
import fr.kevin.llps.conf.event.reminder.entities.AttendeeEntity;
import fr.kevin.llps.conf.event.reminder.entities.PracticeSessionEntity;
import fr.kevin.llps.conf.event.reminder.mapper.PracticeSessionMapper;
import fr.kevin.llps.conf.event.reminder.repository.AttendeeRepository;
import fr.kevin.llps.conf.event.reminder.repository.PracticeSessionRepository;
import fr.kevin.llps.conf.event.reminder.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static fr.kevin.llps.conf.event.reminder.samples.PracticeSessionEntitySample.practiceSessionEntities;
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

    @Mock
    private PracticeSessionMapper practiceSessionMapper;

    @InjectMocks
    private PracticeSessionService practiceSessionService;

    @Test
    void shouldImportPracticeSessions() {
        List<PracticeSessionEntity> practiceSessionEntities = practiceSessionEntities();
        List<AttendeeEntity> attendees = List.of(
                new AttendeeEntity("julien", "arnaud"),
                new AttendeeEntity("mickael", "dupont"),
                new AttendeeEntity("jean", "dupont"),
                new AttendeeEntity("alex", "dubois"));

        practiceSessionService.importPracticeSessions(practiceSessionEntities);

        verify(attendeeRepository).saveAll(attendees);
        verify(practiceSessionRepository).saveAll(practiceSessionEntities);
        verifyNoMoreInteractions(practiceSessionRepository);
    }

    @Test
    void shouldGetUpcomingPracticeSessions() {
        List<PracticeSessionEntity> practiceSessionEntities = practiceSessionEntities();
        List<PracticeSession> expectedPracticeSessions = practiceSessionList();

        when(dateUtils.getCurrentDate()).thenReturn(DATE);
        when(practiceSessionRepository.findByDateLaterThan(DATE)).thenReturn(practiceSessionEntities);
        when(practiceSessionMapper.mapToPracticeSessions(practiceSessionEntities)).thenReturn(expectedPracticeSessions);

        List<PracticeSession> upcomingPracticeSessions = practiceSessionService.getUpcomingPracticeSessions();

        assertThat(upcomingPracticeSessions).containsExactlyInAnyOrderElementsOf(expectedPracticeSessions);

        verifyNoMoreInteractions(dateUtils, practiceSessionRepository, practiceSessionMapper);
    }

    @Test
    void shouldGetAll() {
        List<PracticeSessionEntity> practiceSessionEntities = practiceSessionEntities();
        List<PracticeSession> expectedPracticeSessions = practiceSessionList();

        when(practiceSessionRepository.findAllOrderedByDate()).thenReturn(practiceSessionEntities);
        when(practiceSessionMapper.mapToPracticeSessions(practiceSessionEntities)).thenReturn(expectedPracticeSessions);

        List<PracticeSession> practiceSessions = practiceSessionService.getAll();

        assertThat(practiceSessions).containsExactlyInAnyOrderElementsOf(expectedPracticeSessions);

        verifyNoMoreInteractions(practiceSessionRepository, practiceSessionMapper);
    }

}
