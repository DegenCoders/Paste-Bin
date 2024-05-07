package com.degenCoders.pastebin.jobs;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.degenCoders.pastebin.repository.NoteRepository;

@Component
public class NoteRecordRemover {

    @Autowired
    private NoteRepository noteRepository;
    @Scheduled(cron = "0 0 */1 * * *") // Runs every hour
    public void removeOldRecords() {
        System.out.printf("Removing old records at {}", LocalDateTime.now());
        LocalDateTime oneDayAgo = LocalDateTime.now().minusDays(1);
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);

        // Delete notes without userId older than 1 hour
        noteRepository.deleteByUserIdIsNullAndCreationDateBefore(oneHourAgo);

        // Delete notes with userId older than 1 day
        noteRepository.deleteByUserIdIsNotNullAndCreationDateBefore(oneDayAgo);
    }
}
