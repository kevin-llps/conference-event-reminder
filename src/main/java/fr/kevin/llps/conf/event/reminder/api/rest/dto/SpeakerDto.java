package fr.kevin.llps.conf.event.reminder.api.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SpeakerDto {

    @JsonProperty("firstname")
    private final String firstname;

    @JsonProperty("lastname")
    private final String lastname;

}
