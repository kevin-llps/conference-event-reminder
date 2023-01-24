package fr.kevin.llps.conf.event.reminder.domain;

import fr.kevin.llps.conf.event.reminder.csv.CsvEvent;
import fr.kevin.llps.conf.event.reminder.utils.DateUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static fr.kevin.llps.conf.event.reminder.domain.EventType.BBL;
import static jakarta.persistence.CascadeType.ALL;

@Data
@NoArgsConstructor
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

    public static BBL create(CsvEvent csvEvent) {
        return new BBL(
                csvEvent.title(),
                csvEvent.description(),
                DateUtils.mapToLocalDateTime(csvEvent.date(), csvEvent.time()),
                Speaker.create(csvEvent.speaker()),
                csvEvent.company());
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

    @ManyToOne(cascade = ALL)
    @JoinColumn(name = "speaker_id")
    private Speaker speaker;

    @Column(name = "company", nullable = false, columnDefinition = "VARCHAR(100)")
    private String company;

    @Override
    public String[] getCsvColumns() {
        return new String[]{
                title,
                getEventType(),
                description,
                date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                date.format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                String.format("%s %s", speaker.getFirstname(), speaker.getLastname()),
                "",
                company};
    }

    @Override
    public String getEventType() {
        return BBL;
    }

}
