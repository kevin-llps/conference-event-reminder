package fr.kevin.llps.conf.event.reminder.service;

import fr.kevin.llps.conf.event.reminder.domain.BBL;
import fr.kevin.llps.conf.event.reminder.entities.BBLEntity;
import fr.kevin.llps.conf.event.reminder.mapper.BBLMapper;
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
    private final BBLMapper bblMapper;

    public void importBBLs(List<BBLEntity> bblList) {
        bblRepository.saveAll(bblList);
    }

    public List<BBL> getUpcomingBBLs() {
        LocalDateTime currentDate = dateUtils.getCurrentDate();

        List<BBLEntity> upcomingBBLs = bblRepository.findByDateLaterThan(currentDate);

        return bblMapper.mapToBBLs(upcomingBBLs);
    }

    public List<BBL> getAll() {
        List<BBLEntity> bblEntities = bblRepository.findAllOrderedByDate();

        return bblMapper.mapToBBLs(bblEntities);
    }
}
