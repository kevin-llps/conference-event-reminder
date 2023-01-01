package fr.kevin.llps.conf.event.reminder.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

import static javax.persistence.CascadeType.ALL;

@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "speaker")
public class SpeakerEntity {

    public SpeakerEntity(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    @Id
    @GeneratedValue
    @Column(name = "speaker_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "firstname", nullable = false, columnDefinition = "VARCHAR(100)")
    private String firstname;

    @Column(name = "lastname", nullable = false, columnDefinition = "VARCHAR(100)")
    private String lastname;

    @OneToMany(mappedBy = "speaker", cascade = ALL)
    private List<TalkEntity> talks;

    @OneToMany(mappedBy = "speaker", cascade = ALL)
    private List<BBLEntity> bbls;

    @OneToMany(mappedBy = "speaker", cascade = ALL)
    private List<PracticeSessionEntity> practiceSessions;

}
