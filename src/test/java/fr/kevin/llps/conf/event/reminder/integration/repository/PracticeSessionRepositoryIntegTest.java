package fr.kevin.llps.conf.event.reminder.integration.repository;

import fr.kevin.llps.conf.event.reminder.domain.PracticeSession;
import fr.kevin.llps.conf.event.reminder.repository.PracticeSessionRepository;
import fr.kevin.llps.conf.event.reminder.utils.MySQLContainerTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static fr.kevin.llps.conf.event.reminder.samples.PracticeSessionSample.onePracticeSession;
import static fr.kevin.llps.conf.event.reminder.utils.TestUtils.DATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class PracticeSessionRepositoryIntegTest extends MySQLContainerTest {

    @Autowired
    private PracticeSessionRepository practiceSessionRepository;

    @BeforeEach
    void setUp() {
        practiceSessionRepository.save(onePracticeSession());
    }

    @AfterEach
    void tearDown() {
        practiceSessionRepository.deleteAll();
    }

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
    }

}
