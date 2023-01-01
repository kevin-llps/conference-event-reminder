package fr.kevin.llps.conf.event.reminder.samples;

import fr.kevin.llps.conf.event.reminder.entities.AttendeeEntity;
import fr.kevin.llps.conf.event.reminder.entities.PracticeSessionAttendeeEntity;
import fr.kevin.llps.conf.event.reminder.entities.PracticeSessionEntity;
import fr.kevin.llps.conf.event.reminder.entities.SpeakerEntity;

import java.time.LocalDateTime;
import java.util.List;

public class PracticeSessionEntitySample {

    public static List<PracticeSessionEntity> practiceSessionEntities() {
        PracticeSessionEntity jee = onePracticeSessionEntity();

        AttendeeEntity firstAgileAttendee = new AttendeeEntity("jean", "dupont");
        AttendeeEntity secondAgileAttendee = new AttendeeEntity("alex", "dubois");

        PracticeSessionEntity agile = new PracticeSessionEntity(
                "Agile",
                "Présentation et pratique de la méthode agile",
                LocalDateTime.of(2022, 9, 20, 14, 30, 0),
                new SpeakerEntity("chris", "arr"));

        PracticeSessionAttendeeEntity firstAgilePracticeSessionAttendee = new PracticeSessionAttendeeEntity(agile, firstAgileAttendee);
        PracticeSessionAttendeeEntity secondAgilePracticeSessionAttendee = new PracticeSessionAttendeeEntity(agile, secondAgileAttendee);

        agile.setPracticeSessionAttendees(List.of(firstAgilePracticeSessionAttendee, secondAgilePracticeSessionAttendee));

        return List.of(jee, agile);
    }

    public static PracticeSessionEntity onePracticeSessionEntity() {
        AttendeeEntity firstJeeAttendee = new AttendeeEntity("julien", "arnaud");
        AttendeeEntity secondJeeAttendee = new AttendeeEntity("mickael", "dupont");

        PracticeSessionEntity practiceSessionEntity = new PracticeSessionEntity(
                "JEE",
                "Session pratique JEE",
                LocalDateTime.of(2023, 4, 11, 19, 0, 0),
                new SpeakerEntity("kevin", "llps"));

        PracticeSessionAttendeeEntity firstJeePracticeSessionAttendee = new PracticeSessionAttendeeEntity(practiceSessionEntity, firstJeeAttendee);
        PracticeSessionAttendeeEntity secondJeePracticeSessionAttendee = new PracticeSessionAttendeeEntity(practiceSessionEntity, secondJeeAttendee);

        practiceSessionEntity.setPracticeSessionAttendees(List.of(firstJeePracticeSessionAttendee, secondJeePracticeSessionAttendee));

        return practiceSessionEntity;
    }

}
