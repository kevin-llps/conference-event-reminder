package fr.kevin.llps.conf.event.reminder.repository;

import fr.kevin.llps.conf.event.reminder.domain.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttendeeRepository extends JpaRepository<Attendee, UUID> {
}
