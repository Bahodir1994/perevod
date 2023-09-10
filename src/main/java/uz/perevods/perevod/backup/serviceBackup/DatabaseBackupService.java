package uz.perevods.perevod.backup.serviceBackup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.User;
import uz.perevods.perevod.entitiy.authorization.Users;
import uz.perevods.perevod.entitiy.messaging.MessagingPermission;
import uz.perevods.perevod.repository.authorization.UserRepository;
import uz.perevods.perevod.repository.messaging.MessagingPermissionRepository;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;

@Service
@RequiredArgsConstructor
public class DatabaseBackupService {
    private final UserRepository userRepository;
    private final JdbcTemplate jdbcTemplate;
    private final MessagingPermissionRepository messagingPermissionRepository;
    private final int maxRecordsPerFile = 1000; // Максимальное количество записей в файле
    private boolean sendFinish = true;

    public void callerFunc(){
        String[] tablesToBackup = {"roles", "total_money", "total_money_log", "transactional_money", "transactional_money_log"};
        for (String tableName : tablesToBackup) {
            String sqlQuery = "select * from " + tableName;
            executeSqlQueryAndSaveToJsonFile(sqlQuery, tableName);
        }
        sendFinish = true;
    }

    public void executeSqlQueryAndSaveToJsonFile(String sqlQuery, String tableName) {

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sqlQuery);
        int totalRecords = rows.size();
        int fileIndex = 1;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm");
        String currentDate = dateFormat.format(new Date());

        for (int startIndex = 0; startIndex < totalRecords; startIndex += maxRecordsPerFile) {
            int endIndex = Math.min(startIndex + maxRecordsPerFile, totalRecords);
            List<Map<String, Object>> recordsToWrite = rows.subList(startIndex, endIndex);

            String fileName = tableName + "_" + currentDate + "_" + fileIndex;

            File tempFile;
            try {
                tempFile = TempFileUtil.createTempFile(fileName, ".json");
                String jsonResult = convertListToJson(recordsToWrite);
                TempFileUtil.writeToFile(tempFile, jsonResult);

                sendToAuthUserLogArchive(tempFile);
                tempFile.delete();
            } catch (IOException e) {
                throw new RuntimeException("Error when creating temp file", e);
            }
            fileIndex++;
        }
    }

    private String convertListToJson(List<Map<String, Object>> rows) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(rows);
    }

    private void sendToAuthUserLogArchive(File fileToSend){
        List<MessagingPermission> permissions = messagingPermissionRepository.findAll();
        TelegramBot bot = BotConfig.telegramBot;

        for (MessagingPermission permission: permissions){
            if (permission.getSendLog()){
                while (sendFinish){
                    SendMessage sendMessage = new SendMessage(permission.getSendLogChatId(), "-----"+ new Date(System.currentTimeMillis()) +"------");
                    bot.execute(sendMessage);
                    sendFinish = false;
                }

                try {
                    SendDocument sendDocument = new SendDocument(permission.getSendLogChatId(), fileToSend);
                    SendResponse response = bot.execute(sendDocument);

                    if (response.isOk()) {
                        System.out.println("File " + fileToSend.getName() + " send successfully.");
                    } else {
                        System.out.println("Error: " + response.description());
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Error bot Telegram", e);
                }
            }
        }
    }
}

