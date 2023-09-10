package uz.perevods.perevod.backup.plannerBackup;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uz.perevods.perevod.backup.serviceBackup.DatabaseBackupService;

import java.io.IOException;

@Component
public class ScheduledBackupTask {
    private final DatabaseBackupService backupService;

    public ScheduledBackupTask(DatabaseBackupService backupService) {
        this.backupService = backupService;
    }

    @Scheduled(cron = "0 0 3 * * ?") // Run at 3:00 AM every day
    public void performDatabaseBackup() {
        backupService.callerFunc();
    }
}

