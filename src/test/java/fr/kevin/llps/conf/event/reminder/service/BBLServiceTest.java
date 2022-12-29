package fr.kevin.llps.conf.event.reminder.service;

import fr.kevin.llps.conf.event.reminder.domain.BBL;
import fr.kevin.llps.conf.event.reminder.repository.BBLRepository;
import fr.kevin.llps.conf.event.reminder.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static fr.kevin.llps.conf.event.reminder.samples.BBLSample.bblList;
import static fr.kevin.llps.conf.event.reminder.utils.TestUtils.DATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BBLServiceTest {

    @Mock
    private BBLRepository bblRepository;

    @Mock
    private DateUtils dateUtils;

    @InjectMocks
    private BBLService bblService;

    @Test
    void shouldImportBBLs() {
        List<BBL> bblList = bblList();

        bblService.importBBLs(bblList);

        verify(bblRepository).saveAll(bblList);
        verifyNoMoreInteractions(bblRepository);
    }

    @Test
    void shouldGetUpcomingBBLs() {
        List<BBL> expectedBBLs = bblList();

        when(dateUtils.getCurrentDate()).thenReturn(DATE);
        when(bblRepository.findByDateLaterThan(DATE)).thenReturn(expectedBBLs);

        List<BBL> upcomingBBLs = bblService.getUpcomingBBLs();

        assertThat(upcomingBBLs).containsExactlyInAnyOrderElementsOf(expectedBBLs);

        verifyNoMoreInteractions(dateUtils, bblRepository);
    }

}
