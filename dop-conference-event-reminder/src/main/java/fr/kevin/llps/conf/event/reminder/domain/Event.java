package fr.kevin.llps.conf.event.reminder.domain;

public sealed interface Event permits Talk, BBL, PracticeSession {
}
