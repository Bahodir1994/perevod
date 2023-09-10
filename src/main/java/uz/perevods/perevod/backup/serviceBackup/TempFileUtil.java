package uz.perevods.perevod.backup.serviceBackup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TempFileUtil {
    public static File createTempFile(String prefix, String suffix) throws IOException {
        String tempDir = System.getProperty("java.io.tmpdir");
        File tempFile = File.createTempFile(prefix, suffix, new File(tempDir));
        return tempFile;
    }

    public static void writeToFile(File file, String content) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        }
    }
}
