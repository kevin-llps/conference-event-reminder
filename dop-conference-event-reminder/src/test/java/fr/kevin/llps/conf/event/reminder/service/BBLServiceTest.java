package fr.kevin.llps.conf.event.reminder.service;

import fr.kevin.llps.conf.event.reminder.domain.BBL;
import fr.kevin.llps.conf.event.reminder.entities.BBLEntity;
import fr.kevin.llps.conf.event.reminder.mapper.BBLMapper;
import fr.kevin.llps.conf.event.reminder.repository.BBLRepository;
import fr.kevin.llps.conf.event.reminder.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static fr.kevin.llps.conf.event.reminder.samples.BBLEntitySample.bblEntities;
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

    @Mock
    private BBLMapper bblMapper;

    @InjectMocks
    private BBLService bblService;

    @Test
    void shouldImportBBLs() {
        List<BBLEntity> bblEntities = bblEntities();

        bblService.importBBLs(bblEntities);

        verify(bblRepository).saveAll(bblEntities);
        verifyNoMoreInteractions(bblRepository);
    }

    @Test
    void shouldGetUpcomingBBLs() {
        List<BBLEntity> bblEntities = bblEntities();
        List<BBL> expectedBBLs = bblList();

        when(dateUtils.getCurrentDate()).thenReturn(DATE);
        when(bblRepository.findByDateLaterThan(DATE)).thenReturn(bblEntities);
        when(bblMapper.mapToBBLs(bblEntities)).thenReturn(expectedBBLs);

        List<BBL> upcomingBBLs = bblService.getUpcomingBBLs();

        assertThat(upcomingBBLs).containsExactlyInAnyOrderElementsOf(expectedBBLs);

        verifyNoMoreInteractions(dateUtils, bblRepository, bblMapper);
    }

    @Test
    void shouldGetAll() {
        List<BBLEntity> bblEntities = bblEntities();
        List<BBL> expectedBBLs = bblList();

        when(bblRepository.findAllOrderedByDate()).thenReturn(bblEntities);
        when(bblMapper.mapToBBLs(bblEntities)).thenReturn(expectedBBLs);

        List<BBL> bblList = bblService.getAll();

        assertThat(bblList).containsExactlyInAnyOrderElementsOf(expectedBBLs);

        verifyNoMoreInteractions(bblRepository, bblMapper);
    }

}
