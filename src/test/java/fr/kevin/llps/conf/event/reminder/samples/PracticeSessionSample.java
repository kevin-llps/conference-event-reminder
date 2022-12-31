package fr.kevin.llps.conf.event.reminder.samples;

import fr.kevin.llps.conf.event.reminder.domain.Attendee;
import fr.kevin.llps.conf.event.reminder.domain.PracticeSession;
import fr.kevin.llps.conf.event.reminder.domain.Speaker;

import java.time.LocalDateTime;
import java.util.List;

public class PracticeSessionSample {

    public static List<PracticeSession> practiceSessionList() {
        PracticeSession jee = onePracticeSession();

        Attendee firstAgileAttendee = new Attendee("jean", "dupont");
        Attendee secondAgileAttendee = new Attendee("alex", "dubois");

        PracticeSession agile = new PracticeSession(
                "Agile",
                "Présentation et pratique de la méthode agile",
                LocalDateTime.of(2022, 9, 20, 14, 30, 0),
                new Speaker("chris", "arr"),
                List.of(firstAgileAttendee, secondAgileAttendee));

        return List.of(jee, agile);
    }

    public static PracticeSession onePracticeSession() {
        Attendee firstJeeAttendee = new Attendee("julien", "arnaud");
        Attendee secondJeeAttendee = new Attendee("mickael", "dupont");

        return new PracticeSession(
                "JEE",
                "Session pratique JEE",
                LocalDateTime.of(2023, 4, 11, 19, 0, 0),
                new Speaker("kevin", "llps"),
                List.of(firstJeeAttendee, secondJeeAttendee));
    }

}
