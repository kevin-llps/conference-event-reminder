package fr.kevin.llps.conf.event.reminder.integration.repository;

import fr.kevin.llps.conf.event.reminder.entities.BBLEntity;
import fr.kevin.llps.conf.event.reminder.repository.BBLRepository;
import fr.kevin.llps.conf.event.reminder.utils.MySQLContainerTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static fr.kevin.llps.conf.event.reminder.samples.BBLEntitySample.bblEntities;
import static fr.kevin.llps.conf.event.reminder.utils.TestUtils.DATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class BBLRepositoryIntegTest extends MySQLContainerTest {

    @Autowired
    private BBLRepository bblRepository;

    @BeforeEach
    void setUp() {
        bblRepository.saveAll(bblEntities());
    }

    @AfterEach
    void tearDown() {
        bblRepository.deleteAll();
    }

    @Transactional
    @Test
    void shouldFindUpcomingBBLs() {
        List<BBLEntity> bblEntities = bblRepository.findByDateLaterThan(DATE);

        assertThat(bblEntities).isNotNull()
                .hasSize(1)
                .extracting("title", "description", "date", "speaker.firstname", "speaker.lastname", "company")
                .containsExactlyInAnyOrder(
                        tuple("Spring",
                                "Présentation de Spring",
                                LocalDateTime.of(2023, 3, 24, 12, 30, 0),
                                "kevin", "llps",
                                "Rockstar Corp"));
    }

    @Transactional
    @Test
    void shouldFindAllOrderedByDate() {
        List<BBLEntity> bblEntities = bblRepository.findAllOrderedByDate();

        assertThat(bblEntities).isNotNull()
                .hasSize(2)
                .extracting("title", "description", "date", "speaker.firstname", "speaker.lastname", "company")
                .containsExactlyInAnyOrder(
                        tuple("Git",
                                "Présentation du fonctionnement de Git",
                                LocalDateTime.of(2022, 9, 6, 12, 0, 0),
                                "chris", "arr",
                                "MadMax Corp"),
                        tuple("Spring",
                                "Présentation de Spring",
                                LocalDateTime.of(2023, 3, 24, 12, 30, 0),
                                "kevin", "llps",
                                "Rockstar Corp"));
    }

}
