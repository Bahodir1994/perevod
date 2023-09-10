package uz.perevods.perevod.backup.serviceBackup;

import com.github.kshashov.telegram.api.MessageType;
import com.github.kshashov.telegram.api.TelegramMvcController;
import com.github.kshashov.telegram.api.TelegramRequest;
import com.github.kshashov.telegram.api.bind.annotation.BotController;
import com.github.kshashov.telegram.api.bind.annotation.BotPathVariable;
import com.github.kshashov.telegram.api.bind.annotation.BotRequest;
import com.github.kshashov.telegram.api.bind.annotation.request.MessageRequest;
import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendSticker;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import uz.perevods.perevod.entitiy.authorization.Users;
import uz.perevods.perevod.entitiy.messaging.MessagingPermission;
import uz.perevods.perevod.repository.authorization.UserRepository;
import uz.perevods.perevod.repository.messaging.MessagingPermissionRepository;


import java.io.IOException;

@BotController
@RequiredArgsConstructor
public class MyBot implements TelegramMvcController {
    private final UserRepository userRepository;
    private final MessagingPermissionRepository messagingPermissionRepository;

    @Getter
    @Value("${bot.token}")
    private String token;

    @Override
    public String getToken() {
        return token;
    }

    @BotRequest(value = "/start", type = {MessageType.CALLBACK_QUERY, MessageType.MESSAGE})
    public void startBot(User user, Chat chat) {
        TelegramBot telegramBot = BotConfig.telegramBot;

        SendSticker sendSticker = new SendSticker(chat.id(), "CAACAgIAAxkBAAEKRHNk_hZ4fRjzllcE4wkNeCF8YZy3fgACOAsAAk7kmUsysUfS2U-M0DAE");
        telegramBot.execute(sendSticker);

        String messageText = "<code>&#9888; Diqqat siz avtorizatsiyadan o'tishingiz lozim:</code> Avtorizatsiyadan o'tish uchun login va parolingizni `/login` so'zidan keyin `-` belgisi bilan ajratib yozing.";
        SendMessage sendMessage = new SendMessage(chat.id(), messageText);
        sendMessage.parseMode(ParseMode.HTML); // Specify that the message content uses HTML formatting
        telegramBot.execute(sendMessage);

        SendMessage sendMessageBtn = new SendMessage(chat.id(), "Misol: /login user-password");
        telegramBot.execute(sendMessageBtn);

    }

    @MessageRequest("/login {name:[\\S]+}")
    @Transactional
    public void helloWithName(@BotPathVariable("name") String name, Chat chat) {
        TelegramBot telegramBot = BotConfig.telegramBot;

        int hyphenIndex = name.indexOf('-');

        String login = name.substring(0, hyphenIndex);
        String password = name.substring(hyphenIndex + 1);

        Users users = userRepository.findByUsernameAndPassword(login, password);
        if(users != null){
            messagingPermissionRepository.findByUserId(users.getId()).ifPresentOrElse(
                    messagingPermission -> {
                        String messageText = "<code>&#9888; "+users.getFullName()+"</code>";
                        SendMessage sendMessage = new SendMessage(chat.id(), messageText + " siz avtorizasiyadan o'tgansiz!");
                        sendMessage.parseMode(ParseMode.HTML); // Specify that the message content uses HTML formatting
                        telegramBot.execute(sendMessage);
                    },
                    () -> {
                        String messageText = "<code>&#9888; "+users.getFullName()+"</code>";
                        SendMessage sendMessage = new SendMessage(chat.id(), messageText + " siz avtorizasiyadan o'tdingiz! Endi sizga grafik asosida log fayllar kelib turadi.");
                        sendMessage.parseMode(ParseMode.HTML); // Specify that the message content uses HTML formatting
                        telegramBot.execute(new SendSticker(chat.id(), "CAACAgIAAxkBAAEKRItk_inwCPzgWev6_NSftFLwRSOkzQAC_gADVp29CtoEYTAu-df_MAQ"));
                        telegramBot.execute(sendMessage);

                        MessagingPermission messagingPermission1 = new MessagingPermission();
                        messagingPermission1.setUserId(users.getId());
                        messagingPermission1.setSendLog(true);
                        messagingPermission1.setSendLogChatId(String.valueOf(chat.id()));
                        messagingPermissionRepository.saveAndFlush(messagingPermission1);
                    }
            );
        }else {
            telegramBot.execute(new SendMessage(chat.id(), "siz not'g'ri ma'lumot kiritdingiz!"));
        }
    }

    @MessageRequest("/loginCallback")
    public String helloWithCustomCallback(TelegramRequest request, User user, Chat chat) {
        TelegramBot telegramBot = BotConfig.telegramBot;

        request.setCallback(new Callback() {
            @Override
            public void onResponse(BaseRequest request, BaseResponse baseResponse) {
                    // Check the callback data and respond accordingly
                    if ("/login".equals(request)) {
                        // Handle the "/login" callback (e.g., send a URL)
                        SendMessage urlMessage = new SendMessage(chat.id(), "Visit this URL: https://example.com/login");
                        telegramBot.execute(urlMessage);
                    }
            }

            @Override
            public void onFailure(BaseRequest request, IOException e) {
                // TODO
            }
        });
        return "Hello, " + user.firstName() + "!";
    }
}
