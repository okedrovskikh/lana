package lana.Bot;

import lombok.AllArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
@AllArgsConstructor
public class BotStarter {
    private final LanaBot bot;
    @EventListener(ContextRefreshedEvent.class)
    public void start() throws TelegramApiException {
        try {
            BotSession telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class).registerBot(bot);
            System.out.println("Бот @" + bot.getBotUsername() + " успешно запущен!!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
