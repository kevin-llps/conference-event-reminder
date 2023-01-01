package fr.kevin.llps.conf.event.reminder.repository;

import fr.kevin.llps.conf.event.reminder.domain.PracticeSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface PracticeSessionRepository extends JpaRepository<PracticeSession, UUID> {

    @Query(value = "SELECT * FROM practice_session ps WHERE ps.date >= :date ORDER BY ps.date DESC", nativeQuery = true)
    List<PracticeSession> findByDateLaterThan(@Param("date") LocalDateTime date);

    @Query(value = "SELECT * FROM practice_session ps ORDER BY ps.date", nativeQuery = true)
    List<PracticeSession> findAllOrderedByDate();

}
