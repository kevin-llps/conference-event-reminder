package fr.kevin.llps.conf.event.reminder.mapper;

import fr.kevin.llps.conf.event.reminder.api.rest.dto.SpeakerDto;
import fr.kevin.llps.conf.event.reminder.domain.Speaker;
import fr.kevin.llps.conf.event.reminder.entities.SpeakerEntity;
import org.springframework.stereotype.Component;

@Component
public class SpeakerMapper {

    public SpeakerDto mapToDto(Speaker speaker) {
        return SpeakerDto.builder()
                .firstname(speaker.firstname())
                .lastname(speaker.lastname())
                .build();
    }

    public SpeakerEntity mapToEntity(Speaker speaker) {
        return new SpeakerEntity(speaker.firstname(), speaker.lastname());
    }

    public Speaker mapToSpeaker(SpeakerEntity speakerEntity) {
        return new Speaker(speakerEntity.getFirstname(), speakerEntity.getLastname());
    }

    public Speaker mapStrToSpeaker(String speaker) {
        String[] speakerParts = speaker.split(" ", 2);

        return new Speaker(speakerParts[0], speakerParts[1]);
    }
}
