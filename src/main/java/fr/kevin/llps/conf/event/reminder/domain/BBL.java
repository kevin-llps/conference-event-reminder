package fr.kevin.llps.conf.event.reminder.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static fr.kevin.llps.conf.event.reminder.domain.EventType.BBL;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "bbl")
public class BBL implements Event {

    public BBL(String title, String description, LocalDateTime date, Speaker speaker, String company) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.speaker = speaker;
        this.company = company;
    }

    @Id
    @GeneratedValue
    @Column(name = "bbl_id", nullable = false, columnDefinition = "BINARY(16)")
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

    @Column(name = "company", nullable = false, columnDefinition = "VARCHAR(100)")
    private String company;

    @Override
    public String transformToCsv() {
        return String.format("%s;%s;%s;%s;%s %s;%s",
                title,
                description,
                date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                date.format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                speaker.getFirstname(),
                speaker.getLastname(),
                company);
    }

    @Override
    public String getEventType() {
        return BBL;
    }

}
