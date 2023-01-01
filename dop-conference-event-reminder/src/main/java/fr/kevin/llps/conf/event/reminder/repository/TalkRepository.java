package fr.kevin.llps.conf.event.reminder.repository;

import fr.kevin.llps.conf.event.reminder.entities.TalkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TalkRepository extends JpaRepository<TalkEntity, UUID> {

    @Query(value = "SELECT * FROM talk t WHERE t.date >= :date ORDER BY t.date DESC", nativeQuery = true)
    List<TalkEntity> findByDateLaterThan(@Param("date") LocalDateTime date);

    @Query(value = "SELECT * FROM talk t ORDER BY t.date", nativeQuery = true)
    List<TalkEntity> findAllOrderedByDate();

}
