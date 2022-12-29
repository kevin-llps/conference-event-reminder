package fr.kevin.llps.conf.event.reminder.domain;

import fr.kevin.llps.conf.event.reminder.csv.CsvEvent;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static fr.kevin.llps.conf.event.reminder.samples.CsvEventSample.csvEventList;
import static org.assertj.core.api.Assertions.assertThat;

class TalkTest {

    @Test
    void shouldCreate() {
        CsvEvent csvEvent = csvEventList().get(0);

        Talk talk = Talk.create(csvEvent);

        assertThat(talk.getId()).isNull();
        assertThat(talk.getTitle()).isEqualTo("AWS Cognito");
        assertThat(talk.getDescription()).isEqualTo("Après 2 ans à travailler sur la mise en place de cette solution au PMU, kevin llps nous présentera son retour d'expérience en détaillant les points forts et les points faibles de Cognito.");
        assertThat(talk.getDate()).isEqualTo(LocalDateTime.of(2022, 10, 13, 19, 45, 0));
        assertThat(talk.getSpeaker()).isEqualTo(new Speaker("kevin", "llps"));
    }

}
