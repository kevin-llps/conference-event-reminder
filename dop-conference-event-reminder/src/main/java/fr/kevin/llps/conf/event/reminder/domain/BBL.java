package fr.kevin.llps.conf.event.reminder.domain;

import java.time.LocalDateTime;

public record BBL(
        String title,
        String description,
        LocalDateTime date,
        Speaker speaker,
        String company) implements Event {
}
