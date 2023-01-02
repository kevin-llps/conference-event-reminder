package fr.kevin.llps.conf.event.reminder.service;

import fr.kevin.llps.conf.event.reminder.domain.Talk;
import fr.kevin.llps.conf.event.reminder.repository.TalkRepository;
import fr.kevin.llps.conf.event.reminder.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static fr.kevin.llps.conf.event.reminder.samples.TalkSample.talkList;
import static fr.kevin.llps.conf.event.reminder.utils.TestUtils.DATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TalkServiceTest {

    @Mock
    private TalkRepository talkRepository;

    @Mock
    private DateUtils dateUtils;

    @InjectMocks
    private TalkService talkService;

    @Test
    void shouldImportTalks() {
        List<Talk> talks = talkList();

        talkService.importTalks(talks);

        verify(talkRepository).saveAll(talks);
        verifyNoMoreInteractions(talkRepository);
    }

    @Test
    void shouldGetUpcomingTalks() {
        List<Talk> expectedTalks = talkList();

        when(dateUtils.getCurrentDate()).thenReturn(DATE);
        when(talkRepository.findByDateLaterThan(DATE)).thenReturn(expectedTalks);

        List<Talk> upcomingTalks = talkService.getUpcomingTalks();

        assertThat(upcomingTalks).containsExactlyInAnyOrderElementsOf(expectedTalks);

        verifyNoMoreInteractions(dateUtils, talkRepository);
    }

    @Test
    void shouldGetAll() {
        List<Talk> expectedTalks = talkList();

        when(talkRepository.findAllOrderedByDate()).thenReturn(expectedTalks);

        List<Talk> talks = talkService.getAll();

        assertThat(talks).containsExactlyInAnyOrderElementsOf(expectedTalks);

        verifyNoMoreInteractions(talkRepository);
    }

}
