package fr.kevin.llps.conf.event.reminder.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static fr.kevin.llps.conf.event.reminder.domain.EventType.PRACTICE_SESSION;
import static javax.persistence.CascadeType.ALL;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "practice_session")
public class PracticeSession implements Event {

    public PracticeSession(String title, String description, LocalDateTime date, Speaker speaker) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.speaker = speaker;
    }

    @Id
    @GeneratedValue
    @Column(name = "practice_session_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "title", nullable = false, columnDefinition = "VARCHAR(100)")
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "VARCHAR(1000)")
    private String description;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "speaker_id")
    private Speaker speaker;

    @OneToMany(mappedBy = "practiceSession", cascade = ALL)
    private List<PracticeSessionAttendee> practiceSessionAttendees;

    @Override
    public String transformToCsv() {
        return String.format("%s;%s;%s;%s;%s %s;%s",
                title,
                description,
                date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                date.format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                speaker.getFirstname(),
                speaker.getLastname(),
                getCsvAttendees());
    }

    private String getCsvAttendees() {
        return practiceSessionAttendees.stream()
                .map(PracticeSessionAttendee::getAttendee)
                .map(Attendee::toString)
                .collect(Collectors.joining(";"));
    }

    @Override
    public String getEventType() {
        return PRACTICE_SESSION;
    }

}
