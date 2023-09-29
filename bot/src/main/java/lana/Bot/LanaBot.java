package lana.Bot;


import lana.Bot.Client.BotClient;
import lana.Bot.handlers.KeyBoardHandler;
import lana.Bot.properties.BotProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class LanaBot extends TelegramLongPollingBot {

    private final BotProperties botProperties;
    private final BotClient botClient;

    @Autowired
    public LanaBot(BotProperties botProperties, BotClient botClient) {
        super(botProperties.getToken());
        this.botProperties = botProperties;
        this.botClient = botClient;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            sendMessage(update);
        }
        //тут типа действия при том или ином нажатии кнопки, я пока сделал заглушку такую
        if (update.hasCallbackQuery()) {
            processingCallBackData(update);
        }
    }

    private void sendMessage(Update update) {
        if (!update.hasMessage())
            return;
        KeyBoardHandler keyBoardHandler = new KeyBoardHandler();
        SendMessage sendMessage = SendMessage.builder()
                .text(update.getMessage().getText())
                .chatId(update.getMessage().getChatId())
                .build();

        SendChatAction sendChatAction = new SendChatAction();

        sendChatAction.setChatId(update.getMessage().getChatId());
        sendChatAction.setAction(ActionType.TYPING);

        sendMessage = keyBoardHandler.createInlineKeyBoard(sendMessage);

        try {
            execute(sendChatAction);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return botProperties.getName();
    }

    public void processingCallBackData(Update update) {
        String callData = update.getCallbackQuery().getData();
        if (callData.equals("ACCEPTED")) {
            SendMessage sendMessage = SendMessage.builder()
                    .text("Принял предложку")
                    .chatId(update.getCallbackQuery().getMessage().getChatId())
                    .build();
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else {
            SendMessage sendMessage = SendMessage.builder()
                    .text("Отклонил предложку")
                    .chatId(update.getCallbackQuery().getMessage().getChatId())
                    .build();
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
