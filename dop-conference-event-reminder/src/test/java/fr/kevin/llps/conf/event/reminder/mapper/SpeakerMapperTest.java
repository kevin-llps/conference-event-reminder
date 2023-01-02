package fr.kevin.llps.conf.event.reminder.mapper;

import fr.kevin.llps.conf.event.reminder.api.rest.dto.SpeakerDto;
import fr.kevin.llps.conf.event.reminder.domain.Speaker;
import fr.kevin.llps.conf.event.reminder.entities.SpeakerEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SpeakerMapperTest {

    @Test
    void shouldMapToDto() {
        Speaker speaker = new Speaker("kevin", "llps");

        SpeakerMapper speakerMapper = new SpeakerMapper();

        SpeakerDto speakerDto = speakerMapper.mapToDto(speaker);

        assertThat(speakerDto.firstname()).isEqualTo(speaker.firstname());
        assertThat(speakerDto.lastname()).isEqualTo(speaker.lastname());
    }

    @Test
    void shouldMapToEntity() {
        Speaker speaker = new Speaker("kevin", "llps");

        SpeakerMapper speakerMapper = new SpeakerMapper();

        SpeakerEntity speakerEntity = speakerMapper.mapToEntity(speaker);

        assertThat(speakerEntity.getFirstname()).isEqualTo(speaker.firstname());
        assertThat(speakerEntity.getLastname()).isEqualTo(speaker.lastname());
    }

    @Test
    void shouldMapToSpeaker() {
        SpeakerEntity speakerEntity = new SpeakerEntity("kevin", "llps");

        SpeakerMapper speakerMapper = new SpeakerMapper();

        Speaker speaker = speakerMapper.mapToSpeaker(speakerEntity);

        assertThat(speaker.firstname()).isEqualTo(speakerEntity.getFirstname());
        assertThat(speaker.lastname()).isEqualTo(speakerEntity.getLastname());
    }

    @Test
    void shouldMapStrToSpeaker() {
        String strSpeaker = "kevin llps";

        SpeakerMapper speakerMapper = new SpeakerMapper();

        Speaker speaker = speakerMapper.mapStrToSpeaker(strSpeaker);

        assertThat(speaker.firstname()).isEqualTo("kevin");
        assertThat(speaker.lastname()).isEqualTo("llps");
    }

}
