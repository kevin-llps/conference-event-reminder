package fr.kevin.llps.conf.event.reminder.service;

import fr.kevin.llps.conf.event.reminder.domain.BBL;
import fr.kevin.llps.conf.event.reminder.repository.BBLRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static fr.kevin.llps.conf.event.reminder.samples.BBLSample.bblList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class BBLServiceTest {

    @Mock
    private BBLRepository bblRepository;

    @InjectMocks
    private BBLService bblService;

    @Test
    void shouldImportBBLs() {
        List<BBL> bblList = bblList();

        bblService.importBBLs(bblList);

        verify(bblRepository).saveAll(bblList);
        verifyNoMoreInteractions(bblRepository);
    }

}
