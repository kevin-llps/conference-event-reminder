package fr.kevin.llps.conf.event.reminder.mapper;

import fr.kevin.llps.conf.event.reminder.api.rest.dto.SpeakerDto;
import fr.kevin.llps.conf.event.reminder.api.rest.dto.TalkDto;
import fr.kevin.llps.conf.event.reminder.csv.CsvEvent;
import fr.kevin.llps.conf.event.reminder.domain.Speaker;
import fr.kevin.llps.conf.event.reminder.domain.Talk;
import fr.kevin.llps.conf.event.reminder.entities.SpeakerEntity;
import fr.kevin.llps.conf.event.reminder.entities.TalkEntity;
import fr.kevin.llps.conf.event.reminder.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TalkMapper {

    private final SpeakerMapper speakerMapper;

    public List<TalkDto> mapToDtoList(List<Talk> talks) {
        return talks.stream()
                .map(this::mapToDto)
                .toList();
    }

    private TalkDto mapToDto(Talk talk) {
        SpeakerDto speakerDto = speakerMapper.mapToDto(talk.speaker());

        return TalkDto.builder()
                .title(talk.title())
                .description(talk.description())
                .date(talk.date())
                .speaker(speakerDto)
                .build();
    }

    public List<TalkEntity> mapToEntities(List<Talk> talks) {
        return talks.stream()
                .map(this::mapToEntity)
                .toList();
    }

    private TalkEntity mapToEntity(Talk talk) {
        SpeakerEntity speakerEntity = speakerMapper.mapToEntity(talk.speaker());

        return new TalkEntity(talk.title(), talk.description(), talk.date(), speakerEntity);
    }

    public List<Talk> mapToTalks(List<TalkEntity> talkEntities) {
        return talkEntities.stream()
                .map(this::mapToTalk)
                .toList();
    }

    private Talk mapToTalk(TalkEntity talkEntity) {
        Speaker speaker = speakerMapper.mapToSpeaker(talkEntity.getSpeaker());

        return new Talk(talkEntity.getTitle(), talkEntity.getDescription(), talkEntity.getDate(), speaker);
    }

    public List<Talk> mapCsvEventsToTalks(List<CsvEvent> csvTalks) {
        return csvTalks.stream()
                .map(this::mapCsvEventToTalk)
                .toList();
    }

    private Talk mapCsvEventToTalk(CsvEvent csvEvent) {
        return new Talk(
                csvEvent.title(),
                csvEvent.description(),
                DateUtils.mapToLocalDateTime(csvEvent.date(), csvEvent.time()),
                speakerMapper.mapStrToSpeaker(csvEvent.speaker()));
    }

}
