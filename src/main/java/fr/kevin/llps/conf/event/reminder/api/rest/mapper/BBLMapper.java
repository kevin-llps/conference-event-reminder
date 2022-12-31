package fr.kevin.llps.conf.event.reminder.api.rest.mapper;

import fr.kevin.llps.conf.event.reminder.api.rest.dto.BBLDto;
import fr.kevin.llps.conf.event.reminder.api.rest.dto.SpeakerDto;
import fr.kevin.llps.conf.event.reminder.domain.BBL;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BBLMapper {

    private final SpeakerMapper speakerMapper;

    public List<BBLDto> mapToDtoList(List<BBL> bblList) {
        return bblList.stream()
                .map(this::mapToDto)
                .toList();
    }

    private BBLDto mapToDto(BBL bbl) {
        SpeakerDto speakerDto = speakerMapper.mapToDto(bbl.getSpeaker());

        return BBLDto.builder()
                .title(bbl.getTitle())
                .description(bbl.getDescription())
                .date(bbl.getDate())
                .speaker(speakerDto)
                .company(bbl.getCompany())
                .build();
    }

}
