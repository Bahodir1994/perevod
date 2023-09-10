package uz.perevods.perevod.controller.customRunner;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.perevods.perevod.backup.serviceBackup.DatabaseBackupService;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/custom_runner")
@RequiredArgsConstructor
public class CustomRunnerController {
    private final DatabaseBackupService databaseBackupService;

    @GetMapping("/runner_v1")
    public void runner1() throws IOException {
       databaseBackupService.callerFunc();
    }
}
