package fr.kevin.llps.conf.event.reminder.samples;

import fr.kevin.llps.conf.event.reminder.domain.PracticeSession;
import fr.kevin.llps.conf.event.reminder.domain.Speaker;

import java.time.LocalDateTime;

public class PracticeSessionSample {

    public static PracticeSession onePracticeSession() {
        return new PracticeSession(
                "AWS Lambda",
                "Présentation et développement avec AWS Lambda",
                LocalDateTime.of(2023, 4, 20, 16, 30, 0),
                new Speaker("kevin", "llps"));
    }

}
