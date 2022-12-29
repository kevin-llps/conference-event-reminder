package fr.kevin.llps.conf.event.reminder.api.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BBLDto implements EventDto {

    @JsonProperty("title")
    private final String title;

    @JsonProperty("description")
    private final String description;

    @JsonProperty("date")
    private final LocalDateTime date;

    @JsonProperty("speaker")
    private final SpeakerDto speaker;

    @JsonProperty("company")
    private final String company;

}
