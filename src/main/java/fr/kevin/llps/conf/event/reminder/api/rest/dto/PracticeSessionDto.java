package fr.kevin.llps.conf.event.reminder.api.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PracticeSessionDto implements EventDto{

    @JsonProperty("title")
    private final String title;

    @JsonProperty("description")
    private final String description;

    @JsonProperty("date")
    private final LocalDateTime date;

    @JsonProperty("speaker")
    private final SpeakerDto speaker;

    @JsonProperty("attendees")
    private final List<AttendeeDto> attendees;

}
