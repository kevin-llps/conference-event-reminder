package fr.kevin.llps.conf.event.reminder.service;

import fr.kevin.llps.conf.event.reminder.domain.BBL;
import fr.kevin.llps.conf.event.reminder.repository.BBLRepository;
import fr.kevin.llps.conf.event.reminder.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BBLService {

    private final BBLRepository bblRepository;
    private final DateUtils dateUtils;

    public void importBBLs(List<BBL> bblList) {
        bblRepository.saveAll(bblList);
    }

    public List<BBL> getUpcomingBBLs() {
        LocalDateTime currentDate = dateUtils.getCurrentDate();

        return bblRepository.findByDateLaterThan(currentDate);
    }

    public List<BBL> getAll() {
        return bblRepository.findAllOrderedByDate();
    }
}
