package fr.kevin.llps.conf.event.reminder.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class SpeakerTest {

    @ParameterizedTest
    @MethodSource("provideSpeakerNames")
    void shouldCreate(String strSpeaker, String firstname, String lastname) {
        Speaker speaker = Speaker.create(strSpeaker);

        assertThat(speaker.getFirstname()).isEqualTo(firstname);
        assertThat(speaker.getLastname()).isEqualTo(lastname);
    }

    private static Stream<Arguments> provideSpeakerNames() {
        return Stream.of(
                Arguments.of("kevin llps", "kevin", "llps"),
                Arguments.of("jean-claude dupont", "jean-claude", "dupont"),
                Arguments.of("jean da costa", "jean", "da costa"));
    }
}
