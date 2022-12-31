package fr.kevin.llps.conf.event.reminder.mapper;

import fr.kevin.llps.conf.event.reminder.api.rest.dto.BBLDto;
import fr.kevin.llps.conf.event.reminder.api.rest.dto.SpeakerDto;
import fr.kevin.llps.conf.event.reminder.csv.CsvEvent;
import fr.kevin.llps.conf.event.reminder.domain.BBL;
import fr.kevin.llps.conf.event.reminder.domain.Speaker;
import fr.kevin.llps.conf.event.reminder.entities.BBLEntity;
import fr.kevin.llps.conf.event.reminder.entities.SpeakerEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static fr.kevin.llps.conf.event.reminder.samples.BBLEntitySample.bblEntities;
import static fr.kevin.llps.conf.event.reminder.samples.BBLSample.bblList;
import static fr.kevin.llps.conf.event.reminder.samples.CsvEventSample.csvBBLEvents;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BBLMapperTest {

    @Mock
    private SpeakerMapper speakerMapper;

    @InjectMocks
    private BBLMapper bblMapper;

    @Test
    void shouldMapToDtoList() {
        List<BBL> bblList = bblList();

        Speaker speakerKevLps = new Speaker("kevin", "llps");
        Speaker speakerChrisArr = new Speaker("chris", "arr");

        SpeakerDto speakerKevLpsDto = SpeakerDto.builder()
                .firstname("kevin")
                .lastname("llps")
                .build();

        SpeakerDto speakerChrisArrDto = SpeakerDto.builder()
                .firstname("chris")
                .lastname("arr")
                .build();

        when(speakerMapper.mapToDto(speakerKevLps)).thenReturn(speakerKevLpsDto);
        when(speakerMapper.mapToDto(speakerChrisArr)).thenReturn(speakerChrisArrDto);

        List<BBLDto> bblDtoList = bblMapper.mapToDtoList(bblList);

        assertThat(bblDtoList).isNotNull()
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

    @Test
    void shouldMapToEntities() {
        List<BBL> bblList = bblList();

        Speaker speakerKevLps = new Speaker("kevin", "llps");
        Speaker speakerChrisArr = new Speaker("chris", "arr");

        SpeakerEntity speakerKevLpsEntity = new SpeakerEntity("kevin", "llps");
        SpeakerEntity speakerChrisArrEntity = new SpeakerEntity("chris", "arr");

        when(speakerMapper.mapToEntity(speakerKevLps)).thenReturn(speakerKevLpsEntity);
        when(speakerMapper.mapToEntity(speakerChrisArr)).thenReturn(speakerChrisArrEntity);

        List<BBLEntity> bblEntities = bblMapper.mapToEntities(bblList);

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

    @Test
    void shouldMapToBBLs() {
        List<BBLEntity> bblEntities = bblEntities();

        SpeakerEntity speakerKevLpsEntity = new SpeakerEntity("kevin", "llps");
        SpeakerEntity speakerChrisArrEntity = new SpeakerEntity("chris", "arr");

        Speaker speakerKevLps = new Speaker("kevin", "llps");
        Speaker speakerChrisArr = new Speaker("chris", "arr");

        when(speakerMapper.mapToSpeaker(speakerKevLpsEntity)).thenReturn(speakerKevLps);
        when(speakerMapper.mapToSpeaker(speakerChrisArrEntity)).thenReturn(speakerChrisArr);

        List<BBL> bblList = bblMapper.mapToBBLs(bblEntities);

        assertThat(bblList).isNotNull()
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

    @Test
    void shouldMapCsvEventsToBBLs() {
        List<CsvEvent> csvEvents = csvBBLEvents();

        String strSpeakerKevLps = "kevin llps";
        String strSpeakerChrisArr = "chris arr";

        Speaker speakerKevLps = new Speaker("kevin", "llps");
        Speaker speakerChrisArr = new Speaker("chris", "arr");

        when(speakerMapper.mapStrToSpeaker(strSpeakerKevLps)).thenReturn(speakerKevLps);
        when(speakerMapper.mapStrToSpeaker(strSpeakerChrisArr)).thenReturn(speakerChrisArr);

        List<BBL> bblList = bblMapper.mapCsvEventsToBBLs(csvEvents);

        assertThat(bblList).isNotNull()
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
