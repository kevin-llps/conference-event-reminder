package fr.kevin.llps.conf.event.reminder.samples;

import fr.kevin.llps.conf.event.reminder.domain.BBL;
import fr.kevin.llps.conf.event.reminder.domain.Speaker;

import java.time.LocalDateTime;

public class BBLSample {

    public static BBL oneBBL() {
        return new BBL(
                "Spring AOP",
                "Présentation de Spring AOP",
                LocalDateTime.of(2023, 3, 24, 12, 30, 0),
                new Speaker("kevin", "llps"),
                "Rockstar Corp");
    }

}
