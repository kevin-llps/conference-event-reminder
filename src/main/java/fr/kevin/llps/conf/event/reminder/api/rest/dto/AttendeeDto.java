package fr.kevin.llps.conf.event.reminder.api.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
@Builder
public class AttendeeDto {

    @JsonProperty("firstname")
    private final String firstname;

    @JsonProperty("lastname")
    private final String lastname;

}
