package fr.kevin.llps.conf.event.reminder.domain;

import fr.kevin.llps.conf.event.reminder.csv.CsvEvent;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

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
    void shouldGetCsvColumns() {
        BBL bbl = oneBBL();

        List<String> expectedCsvColumns = List.of(
                "Git",
                "BBL",
                "Présentation du fonctionnement de Git",
                "06/09/2022",
                "12:00:00",
                "chris arr",
                "",
                "MadMax Corp");

        assertThat(bbl.getCsvColumns()).containsExactlyElementsOf(expectedCsvColumns);
    }

}
