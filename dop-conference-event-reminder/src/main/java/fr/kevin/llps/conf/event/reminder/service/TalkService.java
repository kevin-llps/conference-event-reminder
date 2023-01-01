package fr.kevin.llps.conf.event.reminder.service;

import fr.kevin.llps.conf.event.reminder.domain.Talk;
import fr.kevin.llps.conf.event.reminder.entities.TalkEntity;
import fr.kevin.llps.conf.event.reminder.mapper.TalkMapper;
import fr.kevin.llps.conf.event.reminder.repository.TalkRepository;
import fr.kevin.llps.conf.event.reminder.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TalkService {

    private final TalkRepository talkRepository;
    private final DateUtils dateUtils;
    private final TalkMapper talkMapper;

    public void importTalks(List<TalkEntity> talks) {
        talkRepository.saveAll(talks);
    }

    public List<Talk> getUpcomingTalks() {
        LocalDateTime currentDate = dateUtils.getCurrentDate();

        List<TalkEntity> talkEntities = talkRepository.findByDateLaterThan(currentDate);

        return talkMapper.mapToTalks(talkEntities);
    }

    public List<Talk> getAll() {
        List<TalkEntity> talkEntities = talkRepository.findAllOrderedByDate();

        return talkMapper.mapToTalks(talkEntities);
    }

}
