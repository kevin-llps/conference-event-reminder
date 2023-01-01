package fr.kevin.llps.conf.event.reminder.samples;

import fr.kevin.llps.conf.event.reminder.entities.BBLEntity;
import fr.kevin.llps.conf.event.reminder.entities.SpeakerEntity;

import java.time.LocalDateTime;
import java.util.List;

public class BBLEntitySample {

    public static List<BBLEntity> bblEntities() {
        BBLEntity git = oneBBLEntity();

        BBLEntity spring = new BBLEntity(
                "Spring",
                "Présentation de Spring",
                LocalDateTime.of(2023, 3, 24, 12, 30, 0),
                new SpeakerEntity("kevin", "llps"),
                "Rockstar Corp");

        return List.of(spring, git);
    }

    public static BBLEntity oneBBLEntity() {
        return new BBLEntity(
                "Git",
                "Présentation du fonctionnement de Git",
                LocalDateTime.of(2022, 9, 6, 12, 0, 0),
                new SpeakerEntity("chris", "arr"),
                "MadMax Corp");
    }

}
