package fr.kevin.llps.conf.event.reminder.integration.repository;

import fr.kevin.llps.conf.event.reminder.entities.AttendeeEntity;
import fr.kevin.llps.conf.event.reminder.entities.PracticeSessionAttendeeEntity;
import fr.kevin.llps.conf.event.reminder.entities.PracticeSessionEntity;
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

import static fr.kevin.llps.conf.event.reminder.samples.PracticeSessionEntitySample.practiceSessionEntities;
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
        List<PracticeSessionEntity> practiceSessions = practiceSessionEntities();

        attendeeRepository.saveAll(getAttendees(practiceSessions));
        practiceSessionRepository.saveAll(practiceSessions);
    }

    private List<AttendeeEntity> getAttendees(List<PracticeSessionEntity> practiceSessions) {
        return practiceSessions.stream()
                .map(PracticeSessionEntity::getPracticeSessionAttendees)
                .flatMap(Collection::stream)
                .map(PracticeSessionAttendeeEntity::getAttendee)
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
        List<PracticeSessionEntity> practiceSessionEntities = practiceSessionRepository.findByDateLaterThan(DATE);

        assertThat(practiceSessionEntities).isNotNull()
                .hasSize(1)
                .extracting("title", "description", "date", "speaker.firstname", "speaker.lastname")
                .containsExactlyInAnyOrder(
                        tuple("JEE",
                                "Session pratique JEE",
                                LocalDateTime.of(2023, 4, 11, 19, 0, 0),
                                "kevin", "llps"));

        assertThat(practiceSessionEntities.get(0).getPracticeSessionAttendees()).isNotNull()
                .hasSize(2)
                .extracting("attendee.firstname", "attendee.lastname")
                .containsExactlyInAnyOrder(
                        tuple("julien", "arnaud"),
                        tuple("mickael", "dupont"));
    }

    @Transactional
    @Test
    void shouldFindAllOrderedByDate() {
        List<PracticeSessionEntity> practiceSessionEntities = practiceSessionRepository.findAllOrderedByDate();

        assertThat(practiceSessionEntities).isNotNull()
                .hasSize(2)
                .extracting("title", "description", "date", "speaker.firstname", "speaker.lastname")
                .containsExactlyInAnyOrder(
                        tuple("JEE",
                                "Session pratique JEE",
                                LocalDateTime.of(2023, 4, 11, 19, 0, 0),
                                "kevin", "llps"),
                        tuple("Agile",
                                "Présentation et pratique de la méthode agile",
                                LocalDateTime.of(2022, 9, 20, 14, 30, 0),
                                "chris", "arr"));

        assertThat(practiceSessionEntities.get(0).getPracticeSessionAttendees()).isNotNull()
                .hasSize(2)
                .extracting("attendee.firstname", "attendee.lastname")
                .containsExactlyInAnyOrder(
                        tuple("jean", "dupont"),
                        tuple("alex", "dubois"));

        assertThat(practiceSessionEntities.get(1).getPracticeSessionAttendees()).isNotNull()
                .hasSize(2)
                .extracting("attendee.firstname", "attendee.lastname")
                .containsExactlyInAnyOrder(
                        tuple("julien", "arnaud"),
                        tuple("mickael", "dupont"));
    }

}
