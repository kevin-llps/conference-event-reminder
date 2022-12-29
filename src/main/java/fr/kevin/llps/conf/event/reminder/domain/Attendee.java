package fr.kevin.llps.conf.event.reminder.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

import static javax.persistence.CascadeType.ALL;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "attendee")
public class Attendee {

    public Attendee(String firstname, String lastname) {
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
    private List<PracticeSessionAttendee> practiceSessionAttendees;

    @Override
    public String toString() {
        return String.format("%s %s", firstname, lastname);
    }
}
