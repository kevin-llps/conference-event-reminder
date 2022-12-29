package fr.kevin.llps.conf.event.reminder.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AttendeeTest {

    @Test
    void shouldGetAttendeeString() {
        Attendee attendee = new Attendee("Jean", "Dupont");

        assertThat(attendee).hasToString("Jean Dupont");
    }

}
