package fr.kevin.llps.conf.event.reminder.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "practice_session_attendee")
public class PracticeSessionAttendeeEntity {

    public PracticeSessionAttendeeEntity(PracticeSessionEntity practiceSession, AttendeeEntity attendee) {
        this.practiceSession = practiceSession;
        this.attendee = attendee;
    }

    @Id
    @GeneratedValue
    @Column(name = "practice_session_attendee_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "practice_session_id")
    private PracticeSessionEntity practiceSession;

    @ManyToOne
    @JoinColumn(name = "attendee_id")
    private AttendeeEntity attendee;

}
