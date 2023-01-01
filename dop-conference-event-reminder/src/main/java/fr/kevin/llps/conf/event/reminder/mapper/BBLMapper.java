package fr.kevin.llps.conf.event.reminder.mapper;

import fr.kevin.llps.conf.event.reminder.api.rest.dto.BBLDto;
import fr.kevin.llps.conf.event.reminder.api.rest.dto.SpeakerDto;
import fr.kevin.llps.conf.event.reminder.csv.CsvEvent;
import fr.kevin.llps.conf.event.reminder.domain.BBL;
import fr.kevin.llps.conf.event.reminder.domain.Speaker;
import fr.kevin.llps.conf.event.reminder.entities.BBLEntity;
import fr.kevin.llps.conf.event.reminder.entities.SpeakerEntity;
import fr.kevin.llps.conf.event.reminder.utils.DateUtils;
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
        SpeakerDto speakerDto = speakerMapper.mapToDto(bbl.speaker());

        return BBLDto.builder()
                .title(bbl.title())
                .description(bbl.description())
                .date(bbl.date())
                .speaker(speakerDto)
                .company(bbl.company())
                .build();
    }

    public List<BBLEntity> mapToEntities(List<BBL> bblList) {
        return bblList.stream()
                .map(this::mapToEntity)
                .toList();
    }

    private BBLEntity mapToEntity(BBL bbl) {
        SpeakerEntity speakerEntity = speakerMapper.mapToEntity(bbl.speaker());

        return new BBLEntity(bbl.title(), bbl.description(), bbl.date(), speakerEntity, bbl.company());
    }

    public List<BBL> mapToBBLs(List<BBLEntity> bblEntities) {
        return bblEntities.stream()
                .map(this::mapToBBL)
                .toList();
    }

    private BBL mapToBBL(BBLEntity bblEntity) {
        Speaker speaker = speakerMapper.mapToSpeaker(bblEntity.getSpeaker());

        return new BBL(bblEntity.getTitle(), bblEntity.getDescription(), bblEntity.getDate(), speaker, bblEntity.getCompany());
    }

    public List<BBL> mapCsvEventsToBBLs(List<CsvEvent> csvBBLs) {
        return csvBBLs.stream()
                .map(this::mapCsvEventToBBL)
                .toList();
    }

    private BBL mapCsvEventToBBL(CsvEvent csvEvent) {
        return new BBL(
                csvEvent.title(),
                csvEvent.description(),
                DateUtils.mapToLocalDateTime(csvEvent.date(), csvEvent.time()),
                speakerMapper.mapStrToSpeaker(csvEvent.speaker()),
                csvEvent.company());
    }

}
