package fr.kevin.llps.conf.event.reminder.integration.repository;

import fr.kevin.llps.conf.event.reminder.domain.Talk;
import fr.kevin.llps.conf.event.reminder.repository.TalkRepository;
import fr.kevin.llps.conf.event.reminder.utils.MySQLContainerTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static fr.kevin.llps.conf.event.reminder.samples.TalkSample.talkList;
import static fr.kevin.llps.conf.event.reminder.utils.TestUtils.DATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class TalkRepositoryIntegTest extends MySQLContainerTest {

    @Autowired
    private TalkRepository talkRepository;

    @BeforeEach
    void setUp() {
        talkRepository.saveAll(talkList());
    }

    @AfterEach
    void tearDown() {
        talkRepository.deleteAll();
    }

    @Transactional
    @Test
    void shouldFindUpcomingTalks() {
        List<Talk> talks = talkRepository.findByDateLaterThan(DATE);

        assertThat(talks).isNotNull()
                .hasSize(3)
                .extracting("title", "description", "date", "speaker.firstname", "speaker.lastname")
                .containsExactlyInAnyOrder(
                        tuple("Mécanismes d'un bon jeu vidéo",
                                "Passionné de jeux vidéo ou juste curieux de comprendre pourquoi ton copain ou ta copine passe toute ses soirées devant son écran au lieu de venir regarder Top Chef avec toi ? Je te conseille de venir écouter cette conférence pour comprendre une partie des mystères qui entourent l'univers très vaste du jeu vidéo.",
                                LocalDateTime.of(2022, 10, 13, 21, 0, 0),
                                "iza", "elk"),
                        tuple("AWS Lambda",
                                "A travers ce retour d'expérience, kevin llps nous présentera le service AWS Lambda et dans quel contexte l'utiliser.",
                                LocalDateTime.of(2022, 10, 24, 19, 0, 0),
                                "kevin", "llps"),
                        tuple("AWS Lambda - Partie 2",
                                "Deuxième partie de la présentation sur les lambdas AWS",
                                LocalDateTime.of(2023, 2, 9, 19, 0, 0),
                                "kevin", "llps"));
    }

    @Transactional
    @Test
    void shouldFindAllOrderedByDate() {
        List<Talk> talks = talkRepository.findAllOrderedByDate();

        assertThat(talks).isNotNull()
                .hasSize(5)
                .extracting("title", "description", "date", "speaker.firstname", "speaker.lastname")
                .containsExactlyInAnyOrder(
                        tuple("AWS Cognito",
                                "Après 2 ans à travailler sur la mise en place de cette solution au PMU, kevin llps nous présentera son retour d'expérience en détaillant les points forts et les points faibles de Cognito.",
                                LocalDateTime.of(2022, 10, 13, 19, 45, 0),
                                "kevin", "llps"),
                        tuple("Les chatbots",
                                "Les chatbots sont un peu partout aujourd'hui. Voyons le pourquoi du comment et réalisons-en un avec Google DialogFlow, pour aider nos chers formateurs à se libérer des questions les plus fréquentes.",
                                LocalDateTime.of(2022, 10, 13, 19, 0, 0),
                                "charlie", "lcs"),
                        tuple("Mécanismes d'un bon jeu vidéo",
                                "Passionné de jeux vidéo ou juste curieux de comprendre pourquoi ton copain ou ta copine passe toute ses soirées devant son écran au lieu de venir regarder Top Chef avec toi ? Je te conseille de venir écouter cette conférence pour comprendre une partie des mystères qui entourent l'univers très vaste du jeu vidéo.",
                                LocalDateTime.of(2022, 10, 13, 21, 0, 0),
                                "iza", "elk"),
                        tuple("AWS Lambda",
                                "A travers ce retour d'expérience, kevin llps nous présentera le service AWS Lambda et dans quel contexte l'utiliser.",
                                LocalDateTime.of(2022, 10, 24, 19, 0, 0),
                                "kevin", "llps"),
                        tuple("AWS Lambda - Partie 2",
                                "Deuxième partie de la présentation sur les lambdas AWS",
                                LocalDateTime.of(2023, 2, 9, 19, 0, 0),
                                "kevin", "llps"));
    }

}
