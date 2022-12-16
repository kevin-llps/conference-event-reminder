package fr.kevin.llps.conf.event.reminder.api.rest.mapper;

import fr.kevin.llps.conf.event.reminder.api.rest.dto.SpeakerDto;
import fr.kevin.llps.conf.event.reminder.domain.Speaker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SpeakerMapperTest {

    @Test
    void shouldMapToDto() {
        Speaker speaker = new Speaker("kevin", "llps");

        SpeakerMapper speakerMapper = new SpeakerMapper();

        SpeakerDto speakerDto = speakerMapper.mapToDto(speaker);

        assertThat(speakerDto.getFirstname()).isEqualTo(speaker.getFirstname());
        assertThat(speakerDto.getLastname()).isEqualTo(speaker.getLastname());
    }

}
