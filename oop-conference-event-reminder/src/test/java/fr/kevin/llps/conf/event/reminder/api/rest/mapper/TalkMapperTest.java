package fr.kevin.llps.conf.event.reminder.api.rest.mapper;

import fr.kevin.llps.conf.event.reminder.api.rest.dto.SpeakerDto;
import fr.kevin.llps.conf.event.reminder.api.rest.dto.TalkDto;
import fr.kevin.llps.conf.event.reminder.domain.Speaker;
import fr.kevin.llps.conf.event.reminder.domain.Talk;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static fr.kevin.llps.conf.event.reminder.samples.TalkSample.talkList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TalkMapperTest {

    @Mock
    private SpeakerMapper speakerMapper;

    @InjectMocks
    private TalkMapper talkMapper;

    @Test
    void shouldMapToDtoList() {
        List<Talk> talks = talkList();

        Speaker speakerKevLps = new Speaker("kevin", "llps");
        Speaker speakerCharLcs = new Speaker("charlie", "lcs");
        Speaker speakerIzaElk = new Speaker("iza", "elk");

        SpeakerDto speakerKevLpsDto = SpeakerDto.builder()
                .firstname("kevin")
                .lastname("llps")
                .build();

        SpeakerDto speakerCharLcsDto = SpeakerDto.builder()
                .firstname("charlie")
                .lastname("lcs")
                .build();

        SpeakerDto speakerIzaElkDto = SpeakerDto.builder()
                .firstname("iza")
                .lastname("elk")
                .build();

        when(speakerMapper.mapToDto(speakerKevLps)).thenReturn(speakerKevLpsDto);
        when(speakerMapper.mapToDto(speakerCharLcs)).thenReturn(speakerCharLcsDto);
        when(speakerMapper.mapToDto(speakerIzaElk)).thenReturn(speakerIzaElkDto);

        List<TalkDto> talkDtoList = talkMapper.mapToDtoList(talks);

        assertThat(talkDtoList).isNotNull()
                .hasSize(5)
                .extracting("title", "description", "date", "speaker.firstname", "speaker.lastname")
                .containsExactlyInAnyOrder(
                        tuple("AWS Cognito",
                                "Apr??s 2 ans ?? travailler sur la mise en place de cette solution au PMU, kevin llps nous pr??sentera son retour d'exp??rience en d??taillant les points forts et les points faibles de Cognito.",
                                LocalDateTime.of(2022, 10, 13, 19, 45, 0),
                                "kevin", "llps"),
                        tuple("Les chatbots",
                                "Les chatbots sont un peu partout aujourd'hui. Voyons le pourquoi du comment et r??alisons-en un avec Google DialogFlow, pour aider nos chers formateurs ?? se lib??rer des questions les plus fr??quentes.",
                                LocalDateTime.of(2022, 10, 13, 19, 0, 0),
                                "charlie", "lcs"),
                        tuple("M??canismes d'un bon jeu vid??o",
                                "Passionn?? de jeux vid??o ou juste curieux de comprendre pourquoi ton copain ou ta copine passe toute ses soir??es devant son ??cran au lieu de venir regarder Top Chef avec toi ? Je te conseille de venir ??couter cette conf??rence pour comprendre une partie des myst??res qui entourent l'univers tr??s vaste du jeu vid??o.",
                                LocalDateTime.of(2022, 10, 13, 21, 0, 0),
                                "iza", "elk"),
                        tuple("AWS Lambda",
                                "A travers ce retour d'exp??rience, kevin llps nous pr??sentera le service AWS Lambda et dans quel contexte l'utiliser.",
                                LocalDateTime.of(2022, 10, 24, 19, 0, 0),
                                "kevin", "llps"),
                        tuple("AWS Lambda - Partie 2",
                                "Deuxi??me partie de la pr??sentation sur les lambdas AWS",
                                LocalDateTime.of(2023, 2, 9, 19, 0, 0),
                                "kevin", "llps"));
    }

}
