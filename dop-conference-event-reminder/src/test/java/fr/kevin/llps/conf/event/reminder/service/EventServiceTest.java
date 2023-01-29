package fr.kevin.llps.conf.event.reminder.service;

import fr.kevin.llps.conf.event.reminder.api.rest.dto.BBLDto;
import fr.kevin.llps.conf.event.reminder.api.rest.dto.EventDto;
import fr.kevin.llps.conf.event.reminder.api.rest.dto.PracticeSessionDto;
import fr.kevin.llps.conf.event.reminder.csv.CsvEvent;
import fr.kevin.llps.conf.event.reminder.domain.BBL;
import fr.kevin.llps.conf.event.reminder.domain.PracticeSession;
import fr.kevin.llps.conf.event.reminder.domain.Talk;
import fr.kevin.llps.conf.event.reminder.entities.BBLEntity;
import fr.kevin.llps.conf.event.reminder.entities.PracticeSessionEntity;
import fr.kevin.llps.conf.event.reminder.entities.TalkEntity;
import fr.kevin.llps.conf.event.reminder.mapper.BBLMapper;
import fr.kevin.llps.conf.event.reminder.mapper.PracticeSessionMapper;
import fr.kevin.llps.conf.event.reminder.mapper.TalkMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static fr.kevin.llps.conf.event.reminder.samples.BBLDtoSample.oneBBLDto;
import static fr.kevin.llps.conf.event.reminder.samples.BBLEntitySample.oneBBLEntity;
import static fr.kevin.llps.conf.event.reminder.samples.BBLSample.oneBBL;
import static fr.kevin.llps.conf.event.reminder.samples.CsvEventSample.*;
import static fr.kevin.llps.conf.event.reminder.samples.PracticeSessionDtoSample.onePracticeSessionDto;
import static fr.kevin.llps.conf.event.reminder.samples.PracticeSessionEntitySample.onePracticeSessionEntity;
import static fr.kevin.llps.conf.event.reminder.samples.PracticeSessionSample.onePracticeSession;
import static fr.kevin.llps.conf.event.reminder.samples.TalkDtoSample.talkDtoList;
import static fr.kevin.llps.conf.event.reminder.samples.TalkEntitySample.talkEntities;
import static fr.kevin.llps.conf.event.reminder.samples.TalkSample.talkList;
import static fr.kevin.llps.conf.event.reminder.utils.TestUtils.readResource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private TalkService talkService;

    @Mock
    private BBLService bblService;

    @Mock
    private PracticeSessionService practiceSessionService;

    @Mock
    private TalkMapper talkMapper;

    @Mock
    private BBLMapper bblMapper;

    @Mock
    private PracticeSessionMapper practiceSessionMapper;

    @InjectMocks
    private EventService eventService;

    @Captor
    private ArgumentCaptor<List<TalkEntity>> talkListCaptor;

    @Captor
    private ArgumentCaptor<List<BBLEntity>> bblListCaptor;

    @Captor
    private ArgumentCaptor<List<PracticeSessionEntity>> practiceSessionListCaptor;

    @Test
    void shouldImportEvents() {
        List<CsvEvent> csvEvents = new ArrayList<>(csvEventList());

        List<Talk> talkList = talkList();
        BBL bbl = oneBBL();
        PracticeSession practiceSession = onePracticeSession();

        when(talkMapper.mapCsvEventsToTalks(csvTalkEvents())).thenReturn(talkList);
        when(bblMapper.mapCsvEventsToBBLs(List.of(csvBBLEvent()))).thenReturn(List.of(bbl));
        when(practiceSessionMapper.mapCsvEventsToPracticeSessions(List.of(csvPracticeSessionEvent()))).thenReturn(List.of(practiceSession));

        when(talkMapper.mapToEntities(talkList)).thenReturn(talkEntities());
        when(bblMapper.mapToEntities(List.of(oneBBL()))).thenReturn(List.of(oneBBLEntity()));
        when(practiceSessionMapper.mapToEntities(List.of(onePracticeSession()))).thenReturn(List.of(onePracticeSessionEntity()));

        eventService.importEvents(csvEvents);

        verify(talkService).importTalks(talkListCaptor.capture());
        verify(bblService).importBBLs(bblListCaptor.capture());
        verify(practiceSessionService).importPracticeSessions(practiceSessionListCaptor.capture());
        verifyNoMoreInteractions(talkMapper, bblMapper, practiceSessionMapper, talkService, bblService, practiceSessionService);

        List<TalkEntity> talks = talkListCaptor.getValue();

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

        List<BBLEntity> bblList = bblListCaptor.getValue();

        assertThat(bblList).isNotNull()
                .hasSize(1)
                .extracting("title", "description", "date", "speaker.firstname", "speaker.lastname", "company")
                .containsExactlyInAnyOrder(
                        tuple("Git",
                                "Présentation du fonctionnement de Git",
                                LocalDateTime.of(2022, 9, 6, 12, 0, 0),
                                "chris", "arr",
                                "MadMax Corp"));

        List<PracticeSessionEntity> practiceSessions = practiceSessionListCaptor.getValue();

        assertThat(practiceSessions).isNotNull()
                .hasSize(1)
                .extracting("title", "description", "date", "speaker.firstname", "speaker.lastname")
                .containsExactlyInAnyOrder(
                        tuple("JEE",
                                "Session pratique JEE",
                                LocalDateTime.of(2023, 4, 11, 19, 0, 0),
                                "kevin", "llps"));

        Assertions.assertThat(practiceSessions.get(0).getPracticeSessionAttendees()).isNotNull()
                .hasSize(2)
                .extracting("attendee.firstname", "attendee.lastname")
                .containsExactlyInAnyOrder(
                        tuple("julien", "arnaud"),
                        tuple("mickael", "dupont"));
    }

    @Test
    void shouldGetUpcomingEvents() {
        List<Talk> talks = talkList();
        List<BBL> bblList = List.of(oneBBL());
        List<PracticeSession> practiceSessions = List.of(onePracticeSession());

        when(talkService.getUpcomingTalks()).thenReturn(talks);
        when(bblService.getUpcomingBBLs()).thenReturn(bblList);
        when(practiceSessionService.getUpcomingPracticeSessions()).thenReturn(practiceSessions);

        when(talkMapper.mapToDtoList(talks)).thenReturn(talkDtoList());
        when(bblMapper.mapToDtoList(bblList)).thenReturn(List.of(oneBBLDto()));
        when(practiceSessionMapper.mapToDtoList(practiceSessions)).thenReturn(List.of(onePracticeSessionDto()));

        List<EventDto> upcomingEvents = eventService.getUpcomingEvents();

        assertThat(upcomingEvents).isNotNull()
                .hasSize(7)
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
                                "kevin", "llps"),
                        tuple("Git",
                                "Présentation du fonctionnement de Git",
                                LocalDateTime.of(2022, 9, 6, 12, 0, 0),
                                "chris", "arr"),
                        tuple("JEE",
                                "Session pratique JEE",
                                LocalDateTime.of(2023, 4, 11, 19, 0, 0),
                                "kevin", "llps"));

        BBLDto bblDto = (BBLDto) upcomingEvents.get(5);
        assertThat(bblDto.company()).isEqualTo("MadMax Corp");

        PracticeSessionDto practiceSessionDto = (PracticeSessionDto) upcomingEvents.get(6);
        assertThat(practiceSessionDto.attendees()).isNotNull()
                .hasSize(2)
                .extracting("firstname", "lastname")
                .containsExactlyInAnyOrder(
                        tuple("julien", "arnaud"),
                        tuple("mickael", "dupont"));

        verifyNoMoreInteractions(talkService, bblService, practiceSessionService, talkMapper, bblMapper, practiceSessionMapper);
    }

    @Test
    void shouldExportEvents() throws IOException {
        DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
        Resource resource = defaultResourceLoader.getResource("classpath:/csv/events.csv");

        when(talkService.getAll()).thenReturn(talkList());
        when(bblService.getAll()).thenReturn(List.of(oneBBL()));
        when(practiceSessionService.getAll()).thenReturn(List.of(onePracticeSession()));

        byte[] bytes = eventService.exportEvents();

        String exportedEvents = new String(bytes, StandardCharsets.UTF_8);

        assertThat(exportedEvents).isEqualToIgnoringNewLines(readResource(resource));
    }

}
