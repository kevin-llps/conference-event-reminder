package fr.kevin.llps.conf.event.reminder.domain;

import fr.kevin.llps.conf.event.reminder.csv.CsvEvent;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static fr.kevin.llps.conf.event.reminder.samples.CsvEventSample.csvPracticeSessionEvent;
import static fr.kevin.llps.conf.event.reminder.samples.PracticeSessionAttendeeSample.onePracticeSessionAttendee;
import static fr.kevin.llps.conf.event.reminder.samples.PracticeSessionSample.onePracticeSession;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

class PracticeSessionTest {

    @Test
    void shouldCreate() {
        CsvEvent csvEvent = csvPracticeSessionEvent();

        PracticeSession practiceSession = PracticeSession.create(csvEvent);

        assertThat(practiceSession.getId()).isNull();
        assertThat(practiceSession.getTitle()).isEqualTo("JEE");
        assertThat(practiceSession.getDescription()).isEqualTo("Session pratique JEE");
        assertThat(practiceSession.getDate()).isEqualTo(LocalDateTime.of(2023, 4, 11, 19, 0, 0));
        assertThat(practiceSession.getSpeaker()).isEqualTo(new Speaker("kevin", "llps"));

        List<PracticeSessionAttendee> practiceSessionAttendees = practiceSession.getPracticeSessionAttendees();
        assertThat(practiceSessionAttendees).isNotNull()
                .hasSize(2)
                .extracting("attendee.firstname", "attendee.lastname")
                .containsExactlyInAnyOrder(
                        tuple("julien", "arnaud"),
                        tuple("mickael", "dupont"));
    }

    @Test
    void shouldGetCsvColumns() {
        PracticeSession practiceSession = onePracticeSession();
        practiceSession.setPracticeSessionAttendees(List.of(
                onePracticeSessionAttendee(practiceSession, new Attendee("Jean", "Dupont")),
                onePracticeSessionAttendee(practiceSession, new Attendee("Adrien", "Legrand"))));

        List<String> expectedCsvColumns = List.of(
                "JEE",
                "Session pratique",
                "Session pratique JEE",
                "11/04/2023",
                "19:00:00",
                "kevin llps",
                "Adrien Legrand,Jean Dupont",
                "");

        assertThat(practiceSession.getCsvColumns()).containsExactlyElementsOf(expectedCsvColumns);
    }

}
