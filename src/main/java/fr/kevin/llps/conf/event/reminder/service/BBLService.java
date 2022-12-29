package fr.kevin.llps.conf.event.reminder.service;

import fr.kevin.llps.conf.event.reminder.domain.BBL;
import fr.kevin.llps.conf.event.reminder.repository.BBLRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BBLService {

    private final BBLRepository bblRepository;

    public void importBBLs(List<BBL> bblList) {
        bblRepository.saveAll(bblList);
    }

}
