package fr.kevin.llps.conf.event.reminder.domain;

import fr.kevin.llps.conf.event.reminder.csv.CsvEvent;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static fr.kevin.llps.conf.event.reminder.samples.BBLSample.oneBBL;
import static fr.kevin.llps.conf.event.reminder.samples.CsvEventSample.csvBBLEvent;
import static org.assertj.core.api.Assertions.assertThat;

class BBLTest {

    @Test
    void shouldCreate() {
        CsvEvent csvEvent = csvBBLEvent();

        BBL bbl = BBL.create(csvEvent);

        assertThat(bbl.getId()).isNull();
        assertThat(bbl.getTitle()).isEqualTo("Git");
        assertThat(bbl.getDescription()).isEqualTo("Présentation du fonctionnement de Git");
        assertThat(bbl.getDate()).isEqualTo(LocalDateTime.of(2022, 9, 6, 12, 0, 0));
        assertThat(bbl.getSpeaker()).isEqualTo(new Speaker("chris", "arr"));
        assertThat(bbl.getCompany()).isEqualTo("MadMax Corp");
    }

    @Test
    void shouldTransformToCsv() {
        BBL bbl = oneBBL();

        String expectedCsv = """
                Spring;
                Présentation de Spring;
                24/03/2023;
                12:30:00;
                kevin llps;
                Rockstar Corp                
                """;

        assertThat(bbl.transformToCsv()).isEqualToIgnoringNewLines(expectedCsv);
    }

}
