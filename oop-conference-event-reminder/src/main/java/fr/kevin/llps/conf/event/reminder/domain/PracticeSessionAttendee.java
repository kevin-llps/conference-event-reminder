package fr.kevin.llps.conf.event.reminder.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "practice_session_attendee")
public class PracticeSessionAttendee {

    public PracticeSessionAttendee(PracticeSession practiceSession, Attendee attendee) {
        this.practiceSession = practiceSession;
        this.attendee = attendee;
    }

    @Id
    @GeneratedValue
    @Column(name = "practice_session_attendee_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "practice_session_id")
    private PracticeSession practiceSession;

    @ManyToOne
    @JoinColumn(name = "attendee_id")
    private Attendee attendee;

}
