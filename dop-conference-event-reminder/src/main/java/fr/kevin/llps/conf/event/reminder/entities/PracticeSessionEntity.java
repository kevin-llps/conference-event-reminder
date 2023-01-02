package fr.kevin.llps.conf.event.reminder.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static javax.persistence.CascadeType.ALL;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "practice_session")
public class PracticeSessionEntity {

    public PracticeSessionEntity(String title, String description, LocalDateTime date, SpeakerEntity speaker) {
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
    private SpeakerEntity speaker;

    @OneToMany(mappedBy = "practiceSession", cascade = ALL)
    private List<PracticeSessionAttendeeEntity> practiceSessionAttendees;

}
