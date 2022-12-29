package fr.kevin.llps.conf.event.reminder.integration.repository;

import fr.kevin.llps.conf.event.reminder.domain.Attendee;
import fr.kevin.llps.conf.event.reminder.domain.PracticeSession;
import fr.kevin.llps.conf.event.reminder.domain.PracticeSessionAttendee;
import fr.kevin.llps.conf.event.reminder.repository.AttendeeRepository;
import fr.kevin.llps.conf.event.reminder.repository.PracticeSessionRepository;
import fr.kevin.llps.conf.event.reminder.utils.MySQLContainerTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static fr.kevin.llps.conf.event.reminder.samples.PracticeSessionSample.practiceSessionList;
import static fr.kevin.llps.conf.event.reminder.utils.TestUtils.DATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class PracticeSessionRepositoryIntegTest extends MySQLContainerTest {

    @Autowired
    private AttendeeRepository attendeeRepository;

    @Autowired
    private PracticeSessionRepository practiceSessionRepository;

    @BeforeEach
    void setUp() {
        List<PracticeSession> practiceSessions = practiceSessionList();

        attendeeRepository.saveAll(getAttendees(practiceSessions));
        practiceSessionRepository.saveAll(practiceSessions);
    }

    private List<Attendee> getAttendees(List<PracticeSession> practiceSessions) {
        return practiceSessions.stream()
                .map(PracticeSession::getPracticeSessionAttendees)
                .flatMap(Collection::stream)
                .map(PracticeSessionAttendee::getAttendee)
                .toList();
    }

    @AfterEach
    void tearDown() {
        attendeeRepository.deleteAll();
        practiceSessionRepository.deleteAll();
    }

    @Transactional
    @Test
    void shouldFindUpcomingPracticeSessions() {
        List<PracticeSession> practiceSessions = practiceSessionRepository.findByDateLaterThan(DATE);

        assertThat(practiceSessions).isNotNull()
                .hasSize(1)
                .extracting("title", "description", "date", "speaker.firstname", "speaker.lastname")
                .containsExactlyInAnyOrder(
                        tuple("JEE",
                                "Session pratique JEE",
                                LocalDateTime.of(2023, 4, 11, 19, 0, 0),
                                "kevin", "llps"));

        assertThat(practiceSessions.get(0).getPracticeSessionAttendees()).isNotNull()
                .hasSize(2)
                .extracting("attendee.firstname", "attendee.lastname")
                .containsExactlyInAnyOrder(
                        tuple("julien", "arnaud"),
                        tuple("mickael", "dupont"));
    }

}
