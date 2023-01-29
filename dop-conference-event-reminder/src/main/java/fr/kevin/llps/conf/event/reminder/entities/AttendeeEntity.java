package fr.kevin.llps.conf.event.reminder.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

import static javax.persistence.CascadeType.ALL;

@Data
@NoArgsConstructor
@Entity
@Table(name = "attendee")
public class AttendeeEntity {

    public AttendeeEntity(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    @Id
    @GeneratedValue
    @Column(name = "attendee_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "firstname", nullable = false, columnDefinition = "VARCHAR(100)")
    private String firstname;

    @Column(name = "lastname", nullable = false, columnDefinition = "VARCHAR(100)")
    private String lastname;

    @OneToMany(mappedBy = "attendee", cascade = ALL)
    private List<PracticeSessionAttendeeEntity> practiceSessionAttendees;
}
