package fr.kevin.llps.conf.event.reminder.api.rest.mapper;

import fr.kevin.llps.conf.event.reminder.api.rest.dto.SpeakerDto;
import fr.kevin.llps.conf.event.reminder.domain.Speaker;
import org.springframework.stereotype.Component;

@Component
public class SpeakerMapper {

    public SpeakerDto mapToDto(Speaker speaker) {
        return SpeakerDto.builder()
                .firstname(speaker.getFirstname())
                .lastname(speaker.getLastname())
                .build();
    }

}
