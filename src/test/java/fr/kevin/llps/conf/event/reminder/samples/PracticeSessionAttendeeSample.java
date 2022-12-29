package fr.kevin.llps.conf.event.reminder.samples;

import fr.kevin.llps.conf.event.reminder.domain.Attendee;
import fr.kevin.llps.conf.event.reminder.domain.PracticeSession;
import fr.kevin.llps.conf.event.reminder.domain.PracticeSessionAttendee;

public class PracticeSessionAttendeeSample {

    public static PracticeSessionAttendee onePracticeSessionAttendee(PracticeSession practiceSession, Attendee attendee) {
        return new PracticeSessionAttendee(practiceSession, attendee);
    }

}
