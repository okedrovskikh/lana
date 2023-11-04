package lana.bot;

import lana.handlers.KeyBoardHandler;
import lana.post.Post;
import lana.properties.BotProperties;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.GetUpdates;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class LanaBot extends TelegramLongPollingBot {

    private final List<String> adminsID = List.of("772298418", "387209539","441326472");
    private final BotProperties botProperties;
    private final PostCreatorService postCreatorService;

    public LanaBot(BotProperties botProperties, PostCreatorService postCreatorService) {
        super(botProperties.getToken());
        this.postCreatorService = postCreatorService;
        this.botProperties = botProperties;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            List<User> users = update.getMessage().getNewChatMembers();
            if(users.size() != 0) {
                checkUpdatesAdmins(users);
            }
        }

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
        int messageId = update.getCallbackQuery().getMessage().getMessageId();
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        String answer = callData.equals(BotCallbacks.ACCEPT.getCallbackData()) ?
                "Принял предложку" : getReactionOfReject(update.getCallbackQuery().getMessage().getText());
        EditMessageText editMessageText = EditMessageText.builder()
                .chatId(chatId)
                .messageId(messageId)
                .text(answer).parseMode("MarkdownV2").build();
        editMessage(editMessageText);
    }

    private void editMessage(EditMessageText editMessageText) {
        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
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
        //TODO: делегировать пост крейтор сервису создание поста для бд
//        Message message = update.getMessage();getMessage
//        Long userID = message.getChat().getId();
//        String text = message.getText();
//        //TODO: научиться получать контент из сообщения
//        Byte[] binaryData = null;
//        Post post = postCreatorService.generatePost(userID,);

        KeyBoardHandler keyBoardHandler = new KeyBoardHandler();

        for (var adminID : adminsID) {
            SendMessage sendMessage = SendMessage.builder()
                    .text(update.getMessage().getText())
                    .chatId(adminID)
                    .build();
            sendMessage = keyBoardHandler.createInlineKeyBoard(sendMessage);
            sendMessage(sendMessage);
        }
    }

    private String getReactionOfReject(String answer) {
        return "~" + answer + "~";
    }

    public void checkUpdatesAdmins(List<User> users) {
        for(var user: users) {
            try {
                Long botId = this.getMe().getId();
                if (user.getId() == botId) {
                    System.out.println("I am added");
                }

            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

//    private static boolean checkIfBotIsAdmin(Long chatId, long id) {
//        GetChatMember getChatMember = new GetChatMember(chatId, id);
//        try {
//            ChatMember chatMember = bot.execute(getChatMember);
//            return chatMember instanceof ChatMemberAdministrator;
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
}
