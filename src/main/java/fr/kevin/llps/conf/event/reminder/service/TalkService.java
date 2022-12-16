package fr.kevin.llps.conf.event.reminder.service;

import fr.kevin.llps.conf.event.reminder.domain.Talk;
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

    public void importTalks(List<Talk> talks) {
        talkRepository.saveAll(talks);
    }

    public List<Talk> getUpcomingTalks() {
        LocalDateTime currentDate = dateUtils.getCurrentDate();

        return talkRepository.findByDateLaterThan(currentDate);
    }

}
