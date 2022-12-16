package fr.kevin.llps.conf.event.reminder.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.SECONDS;

@Component
@RequiredArgsConstructor
public class DateUtils {

    private final Clock clock;

    public LocalDateTime getCurrentDate() {
        return LocalDateTime.now(clock).truncatedTo(SECONDS);
    }

}
