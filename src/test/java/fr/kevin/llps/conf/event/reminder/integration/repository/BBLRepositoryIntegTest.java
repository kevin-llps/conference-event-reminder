package fr.kevin.llps.conf.event.reminder.integration.repository;

import fr.kevin.llps.conf.event.reminder.domain.BBL;
import fr.kevin.llps.conf.event.reminder.repository.BBLRepository;
import fr.kevin.llps.conf.event.reminder.utils.MySQLContainerTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static fr.kevin.llps.conf.event.reminder.samples.BBLSample.bblList;
import static fr.kevin.llps.conf.event.reminder.utils.TestUtils.DATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class BBLRepositoryIntegTest extends MySQLContainerTest {

    @Autowired
    private BBLRepository bblRepository;

    @BeforeEach
    void setUp() {
        bblRepository.saveAll(bblList());
    }

    @AfterEach
    void tearDown() {
        bblRepository.deleteAll();
    }

    @Test
    void shouldFindUpcomingBBLs() {
        List<BBL> bblList = bblRepository.findByDateLaterThan(DATE);

        assertThat(bblList).isNotNull()
                .hasSize(1)
                .extracting("title", "description", "date", "speaker.firstname", "speaker.lastname", "company")
                .containsExactlyInAnyOrder(
                        tuple("Spring",
                                "Présentation de Spring",
                                LocalDateTime.of(2023, 3, 24, 12, 30, 0),
                                "kevin", "llps",
                                "Rockstar Corp"));
    }

}
