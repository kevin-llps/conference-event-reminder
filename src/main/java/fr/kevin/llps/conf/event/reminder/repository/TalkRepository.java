package fr.kevin.llps.conf.event.reminder.repository;

import fr.kevin.llps.conf.event.reminder.domain.Talk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TalkRepository extends JpaRepository<Talk, UUID> {

    @Query(value = "SELECT * FROM talk t WHERE t.date >= :date ORDER BY t.date DESC", nativeQuery = true)
    List<Talk> findByDateLaterThan(@Param("date") LocalDateTime date);

}
