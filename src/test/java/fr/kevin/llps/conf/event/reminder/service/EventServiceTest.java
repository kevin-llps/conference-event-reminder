package fr.kevin.llps.conf.event.reminder.service;

import fr.kevin.llps.conf.event.reminder.api.rest.dto.EventDto;
import fr.kevin.llps.conf.event.reminder.api.rest.mapper.TalkMapper;
import fr.kevin.llps.conf.event.reminder.csv.CsvEvent;
import fr.kevin.llps.conf.event.reminder.domain.Talk;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static fr.kevin.llps.conf.event.reminder.samples.CsvEventSample.csvEventList;
import static fr.kevin.llps.conf.event.reminder.samples.TalkDtoSample.talkDtoList;
import static fr.kevin.llps.conf.event.reminder.samples.TalkSample.talkList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private TalkService talkService;

    @Mock
    private TalkMapper talkMapper;

    @InjectMocks
    private EventService eventService;

    @Captor
    private ArgumentCaptor<List<Talk>> talkListCaptor;

    @Test
    void shouldImportEvents() {
        CsvEvent csvEventDifferentFromTalk = CsvEvent.builder()
                .title("Different from Talk")
                .type("Different from Talk")
                .description("Different from Talk")
                .date("13/10/2022")
                .time("22:00:00")
                .speaker("kevin llps")
                .build();

        List<CsvEvent> csvEvents = new ArrayList<>(csvEventList());
        csvEvents.add(csvEventDifferentFromTalk);

        eventService.importEvents(csvEvents);

        verify(talkService).importTalks(talkListCaptor.capture());

        List<Talk> talks = talkListCaptor.getValue();

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
    void shouldGetUpcomingEvents() {
        List<Talk> talks = talkList();

        when(talkService.getUpcomingTalks()).thenReturn(talks);
        when(talkMapper.mapToDto(talks)).thenReturn(talkDtoList());

        List<EventDto> upcomingEvents = eventService.getUpcomingEvents();

        assertThat(upcomingEvents).isNotNull()
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
