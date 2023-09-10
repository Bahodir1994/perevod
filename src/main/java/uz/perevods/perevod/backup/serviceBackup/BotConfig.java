package uz.perevods.perevod.backup.serviceBackup;

import com.github.kshashov.telegram.config.TelegramBotGlobalProperties;
import com.github.kshashov.telegram.config.TelegramBotGlobalPropertiesConfiguration;
import com.pengrad.telegrambot.TelegramBot;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BotConfig implements TelegramBotGlobalPropertiesConfiguration {
    @Getter
    @Value("${bot.token}")
    private String token;

    public static TelegramBot telegramBot;

    @Override
    public void configure(TelegramBotGlobalProperties.Builder builder) {
        builder.processBot(token, bot -> {
            telegramBot = bot;
        });
    }
}
