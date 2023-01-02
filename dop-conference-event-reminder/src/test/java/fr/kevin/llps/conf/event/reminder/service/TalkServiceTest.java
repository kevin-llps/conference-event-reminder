package fr.kevin.llps.conf.event.reminder.service;

import fr.kevin.llps.conf.event.reminder.domain.Talk;
import fr.kevin.llps.conf.event.reminder.entities.TalkEntity;
import fr.kevin.llps.conf.event.reminder.mapper.TalkMapper;
import fr.kevin.llps.conf.event.reminder.repository.TalkRepository;
import fr.kevin.llps.conf.event.reminder.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static fr.kevin.llps.conf.event.reminder.samples.TalkEntitySample.talkEntities;
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

    @Mock
    private TalkMapper talkMapper;

    @InjectMocks
    private TalkService talkService;

    @Test
    void shouldImportTalks() {
        List<TalkEntity> talkEntities = talkEntities();

        talkService.importTalks(talkEntities);

        verify(talkRepository).saveAll(talkEntities);
        verifyNoMoreInteractions(talkRepository);
    }

    @Test
    void shouldGetUpcomingTalks() {
        List<TalkEntity> talkEntities = talkEntities();
        List<Talk> expectedTalks = talkList();

        when(dateUtils.getCurrentDate()).thenReturn(DATE);
        when(talkRepository.findByDateLaterThan(DATE)).thenReturn(talkEntities);
        when(talkMapper.mapToTalks(talkEntities)).thenReturn(expectedTalks);

        List<Talk> upcomingTalks = talkService.getUpcomingTalks();

        assertThat(upcomingTalks).containsExactlyInAnyOrderElementsOf(expectedTalks);

        verifyNoMoreInteractions(dateUtils, talkRepository, talkMapper);
    }

    @Test
    void shouldGetAll() {
        List<TalkEntity> talkEntities = talkEntities();
        List<Talk> expectedTalks = talkList();

        when(talkRepository.findAllOrderedByDate()).thenReturn(talkEntities);
        when(talkMapper.mapToTalks(talkEntities)).thenReturn(expectedTalks);

        List<Talk> talks = talkService.getAll();

        assertThat(talks).containsExactlyInAnyOrderElementsOf(expectedTalks);

        verifyNoMoreInteractions(talkRepository, talkMapper);
    }

}
