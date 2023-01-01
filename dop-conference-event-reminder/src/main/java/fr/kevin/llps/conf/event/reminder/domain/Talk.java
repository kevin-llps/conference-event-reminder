package fr.kevin.llps.conf.event.reminder.domain;

import java.time.LocalDateTime;

public record Talk(
        String title,
        String description,
        LocalDateTime date,
        Speaker speaker) implements Event {
}
