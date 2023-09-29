package lana.Bot;


import lana.Bot.Client.BotClient;
import lana.Bot.handlers.KeyBoardHandler;
import lana.Bot.properties.BotCallbacks;
import lana.Bot.properties.BotProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class LanaBot extends TelegramLongPollingBot {
    private final List<String> adminsID = List.of("772298418", "387209539");
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

        //тупая заглушка чтобы просто протестить перессылку админу
        if (update.hasMessage()) {
            createMessageToAdminsApprove(update);
        }
        //тут типа действия при том или ином нажатии кнопки, я пока сделал заглушку такую
         if (update.hasCallbackQuery()) {
            processingCallBackData(update);
        }
    }

    @Override
    public String getBotUsername() {
        return botProperties.getName();
    }

    private void processingCallBackData(Update update) {

        String callData = update.getCallbackQuery().getData();

        String answer = callData.equals(BotCallbacks.ACCEPT.getCallbackData()) ? "Принял предложку" : "Отклонил предложку";

        SendMessage sendMessage = SendMessage.builder()
                .text(answer)
                .chatId(update.getCallbackQuery().getMessage().getChatId())
                .build();

        sendMessage(sendMessage);
    }

    private void sendMessage(SendMessage sendMessage) {
        SendChatAction sendChatAction = SendChatAction.builder()
                .chatId(sendMessage.getChatId())
                .action(ActionType.TYPING.toString())
                .build();

        try {
            execute(sendMessage);
            execute(sendChatAction);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    private void createMessageToAdminsApprove(Update update) {
        KeyBoardHandler keyBoardHandler = new KeyBoardHandler();
        for(var adminID : adminsID) {
            SendMessage sendMessage = SendMessage.builder()
                    .text(update.getMessage().getText())
                    .chatId(adminID)
                    .build();
            sendMessage = keyBoardHandler.createInlineKeyBoard(sendMessage);
            sendMessage(sendMessage);
        }
    }
}
