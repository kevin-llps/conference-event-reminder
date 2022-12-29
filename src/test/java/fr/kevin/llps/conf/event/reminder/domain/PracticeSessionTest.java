package fr.kevin.llps.conf.event.reminder.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static fr.kevin.llps.conf.event.reminder.samples.PracticeSessionAttendeeSample.onePracticeSessionAttendee;
import static fr.kevin.llps.conf.event.reminder.samples.PracticeSessionSample.onePracticeSession;
import static org.assertj.core.api.Assertions.assertThat;

class PracticeSessionTest {

    @Test
    void shouldTransformToCsv() {
        PracticeSession practiceSession = onePracticeSession();
        practiceSession.setPracticeSessionAttendees(List.of(
                onePracticeSessionAttendee(practiceSession, new Attendee("Jean", "Dupont")),
                onePracticeSessionAttendee(practiceSession, new Attendee("Adrien", "Legrand"))));

        String expectedCsv = """
                AWS Lambda;
                Présentation et développement avec AWS Lambda;
                20/04/2023;
                16:30:00;
                kevin llps;
                Jean Dupont;
                Adrien Legrand                
                """;

        assertThat(practiceSession.transformToCsv()).isEqualToIgnoringNewLines(expectedCsv);
    }

}
