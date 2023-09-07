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

//    @Scheduled(fixedRate = 30000) // Run every 30 seconds
//    public void performDatabaseBackup() {
//        try {
//            String username = "postgres";
//            String databaseName = "postgres";
//            String backupFilePath = "C:\\backup\\DatabaseBackups"; // Windows
//            backupService.createDatabaseBackup(username, databaseName, backupFilePath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}

