package fr.kevin.llps.conf.event.reminder.service;

import fr.kevin.llps.conf.event.reminder.domain.Talk;
import fr.kevin.llps.conf.event.reminder.repository.TalkRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static fr.kevin.llps.conf.event.reminder.samples.TalkSample.talkList;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TalkServiceTest {

    @Mock
    private TalkRepository talkRepository;

    @InjectMocks
    private TalkService talkService;

    @Test
    void shouldImportTalks() {
        List<Talk> talks = talkList();

        when(talkRepository.saveAll(talks)).thenReturn(null);

        talkService.importTalks(talks);

        verifyNoMoreInteractions(talkRepository);
    }

}
