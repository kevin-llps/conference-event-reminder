package fr.kevin.llps.conf.event.reminder.domain;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public record PracticeSession(
        String title,
        String description,
        LocalDateTime date,
        Speaker speaker,
        List<Attendee> attendees) implements Event {

    public PracticeSession(String title, String description, LocalDateTime date, Speaker speaker, List<Attendee> attendees) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.speaker = speaker;
        this.attendees = Collections.unmodifiableList(attendees);
    }

}
