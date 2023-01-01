package fr.kevin.llps.conf.event.reminder.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class AttendeeTest {

    @ParameterizedTest
    @MethodSource("provideAttendeeNames")
    void shouldCreate(String strAttendee, String firstname, String lastname) {
        Attendee attendee = Attendee.create(strAttendee);

        assertThat(attendee.getFirstname()).isEqualTo(firstname);
        assertThat(attendee.getLastname()).isEqualTo(lastname);
    }

    private static Stream<Arguments> provideAttendeeNames() {
        return Stream.of(
                Arguments.of("kevin llps", "kevin", "llps"),
                Arguments.of("jean-claude dupont", "jean-claude", "dupont"),
                Arguments.of("jean da costa", "jean", "da costa"));
    }

    @Test
    void shouldGetAttendeeString() {
        Attendee attendee = new Attendee("Jean", "Dupont");

        assertThat(attendee).hasToString("Jean Dupont");
    }

}
