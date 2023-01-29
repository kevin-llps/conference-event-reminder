package fr.kevin.llps.conf.event.reminder.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static fr.kevin.llps.conf.event.reminder.samples.PracticeSessionSample.onePracticeSession;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class PracticeSessionTest {

    @Test
    void shouldGetUnmodifiableAttendeeList() {
        Attendee firstJeeAttendee = new Attendee("julien", "arnaud");
        Attendee secondJeeAttendee = new Attendee("mickael", "dupont");
        List<Attendee> expectedAttendees = List.of(firstJeeAttendee, secondJeeAttendee);

        Attendee newAttendee = new Attendee("jean", "lagrotte");

        PracticeSession practiceSession = onePracticeSession();

        List<Attendee> attendees = practiceSession.attendees();

        assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(() -> attendees.add(newAttendee));

        assertThat(attendees).containsExactlyInAnyOrderElementsOf(expectedAttendees);
    }

}
