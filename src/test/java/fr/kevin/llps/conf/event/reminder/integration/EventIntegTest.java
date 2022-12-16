package fr.kevin.llps.conf.event.reminder.integration;

import fr.kevin.llps.conf.event.reminder.domain.Talk;
import fr.kevin.llps.conf.event.reminder.repository.TalkRepository;
import fr.kevin.llps.conf.event.reminder.utils.DateUtils;
import fr.kevin.llps.conf.event.reminder.utils.MySQLContainerTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static fr.kevin.llps.conf.event.reminder.samples.TalkSample.talkList;
import static fr.kevin.llps.conf.event.reminder.utils.TestUtils.DATE;
import static fr.kevin.llps.conf.event.reminder.utils.TestUtils.readResource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class EventIntegTest extends MySQLContainerTest {

    @Value("classpath:/json/upcomingEvents.json")
    private Resource upcomingEvents;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TalkRepository talkRepository;

    @MockBean
    private DateUtils dateUtils;

    @AfterEach
    void tearDown() {
        talkRepository.deleteAll();
    }

    @Test
    void shouldImportEvents() throws Exception {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:/csv/events.csv");

        MockMultipartFile fileToImport = new MockMultipartFile("file",
                "events.csv",
                MediaType.TEXT_PLAIN_VALUE,
                resource.getInputStream());

        mockMvc.perform(multipart("/events/import")
                        .file(fileToImport))
                .andExpect(status().isNoContent());

        List<Talk> talks = talkRepository.findAll();

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

    @Test
    void shouldGetUpcomingEvents() throws Exception {
        talkRepository.saveAll(talkList());

        when(dateUtils.getCurrentDate()).thenReturn(DATE);

        mockMvc.perform(get("/events/upcoming"))
                .andExpect(status().isOk())
                .andExpect(content().json(readResource(upcomingEvents)));
    }

}
