package fr.kevin.llps.conf.event.reminder.domain;

import org.junit.jupiter.api.Test;

import static fr.kevin.llps.conf.event.reminder.samples.BBLSample.oneBBL;
import static org.assertj.core.api.Assertions.assertThat;

class BBLTest {

    @Test
    void shouldTransformToCsv() {
        BBL bbl = oneBBL();

        String expectedCsv = """
                Spring AOP;
                Présentation de Spring AOP;
                24/03/2023;
                12:30:00;
                kevin llps;
                Rockstar Corp                
                """;

        assertThat(bbl.transformToCsv()).isEqualToIgnoringNewLines(expectedCsv);
    }

}
