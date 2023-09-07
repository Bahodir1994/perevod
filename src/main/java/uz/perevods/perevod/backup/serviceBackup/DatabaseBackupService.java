package uz.perevods.perevod.backup.serviceBackup;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class DatabaseBackupService {
    public void createDatabaseBackup(String username, String databaseName, String backupFilePath) throws IOException {
        String command = "pg_dump -U " + username + " -d " + databaseName + " -f " + backupFilePath;
        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));

        // Set any environment variables or working directory as needed
        // processBuilder.environment().put("ENV_VAR_NAME", "VALUE");
        processBuilder.directory(new File(backupFilePath));

        Process process = processBuilder.start();

        // You can check the exit status or wait for the process to complete if needed
        int exitCode;
        try {
            exitCode = process.waitFor();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Backup process interrupted", e);
        }

        if (exitCode != 0) {
            throw new RuntimeException("Backup process failed with exit code: " + exitCode);
        }
    }
}

