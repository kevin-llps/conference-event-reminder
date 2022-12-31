package fr.kevin.llps.conf.event.reminder.domain;

import java.time.LocalDateTime;
import java.util.List;

public record PracticeSession(
        String title,
        String description,
        LocalDateTime date,
        Speaker speaker,
        List<Attendee> attendees) implements Event {
}
