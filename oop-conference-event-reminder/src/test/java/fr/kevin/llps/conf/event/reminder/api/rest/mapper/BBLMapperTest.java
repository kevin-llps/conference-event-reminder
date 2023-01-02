package fr.kevin.llps.conf.event.reminder.api.rest.mapper;

import fr.kevin.llps.conf.event.reminder.api.rest.dto.BBLDto;
import fr.kevin.llps.conf.event.reminder.api.rest.dto.SpeakerDto;
import fr.kevin.llps.conf.event.reminder.domain.BBL;
import fr.kevin.llps.conf.event.reminder.domain.Speaker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static fr.kevin.llps.conf.event.reminder.samples.BBLSample.bblList;
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

}
