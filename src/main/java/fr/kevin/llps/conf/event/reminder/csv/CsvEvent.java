package fr.kevin.llps.conf.event.reminder.csv;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class CsvEvent {

    private final String title;
    private final String type;
    private final String description;
    private final String date;
    private final String time;
    private final String speaker;

}
