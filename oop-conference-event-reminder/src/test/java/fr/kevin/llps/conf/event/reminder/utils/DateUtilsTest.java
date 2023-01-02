package fr.kevin.llps.conf.event.reminder.utils;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class DateUtilsTest {

    private static final ZoneId ZONE_ID = ZoneId.of("Europe/Paris");

    private final Clock fixedClock = Clock.fixed(
            ZonedDateTime.parse("2022-10-13T10:30:12.043+01:00[Europe/Paris]").toInstant(),
            ZONE_ID);

    @Test
    void shouldGetCurrentDate() {
        DateUtils dateUtils = new DateUtils(fixedClock);

        LocalDateTime currentDate = dateUtils.getCurrentDate();

        assertThat(currentDate).isEqualTo("2022-10-13T11:30:12");
    }

    @Test
    void shouldMapToLocalDateTime() {
        LocalDateTime localDateTime = DateUtils.mapToLocalDateTime("13/10/2022", "11:30:12");

        assertThat(localDateTime).isEqualTo(LocalDateTime.of(2022, 10, 13, 11, 30, 12, 0));
    }

}
