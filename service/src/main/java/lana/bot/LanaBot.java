package lana.bot;

import lana.handlers.KeyBoardHandler;
import lana.properties.BotProperties;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatAdministrators;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberAdministrator;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberBanned;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberLeft;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class LanaBot extends TelegramLongPollingBot {
    private final BotProperties botProperties;
    private final PostCreatorService postCreatorService;
    private final GroupService groupService;
    private final UserTelegramService userTelegramService;

    public LanaBot(BotProperties botProperties, PostCreatorService postCreatorService, GroupService groupService, UserTelegramService userTelegramService) {
        super(botProperties.getToken());
        this.botProperties = botProperties;
        this.postCreatorService = postCreatorService;
        this.groupService = groupService;
        this.userTelegramService = userTelegramService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMyChatMember()) {
            validateChatMembers(update);
        }

        if (update.hasMessage() && update.getMessage().getChat().getType().equals("private")) {
            createMessageToAdminsApprove(update);
        }

        if (update.hasCallbackQuery()) {
            processingCallBackData(update);
        }
    }


    @Override
    public String getBotUsername() {
        return botProperties.getName();
    }

    private void processingCallBackData(Update update) {
        SendPhoto sendMessage = new SendPhoto();
        String tag;
        List<MessageEntity> msg = update.getCallbackQuery().getMessage().getCaptionEntities();
        if (msg != null) {
            tag = msg.get(0).getText();
        } else {
            tag = update.getCallbackQuery().getMessage().getEntities().get(0).getText();
        }
        sendMessage.setChatId(groupService.getChannel(tag));
        String callData = update.getCallbackQuery().getData();
        int messageId = update.getCallbackQuery().getMessage().getMessageId();
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        String answer;
        if (update.getCallbackQuery().getMessage().getCaption() != null) {
             answer = callData.equals(BotCallbacks.ACCEPT.getCallbackData()) ?
                    "Принял предложку" : getReactionOfReject(update.getCallbackQuery().getMessage().getCaption());
        } else {
            answer = callData.equals(BotCallbacks.ACCEPT.getCallbackData()) ?
                    "Принял предложку" : getReactionOfReject(update.getCallbackQuery().getMessage().getText());
        }
        boolean b = false;
        if (callData.equals(BotCallbacks.ACCEPT.getCallbackData()) &&
                update.getCallbackQuery().getMessage().getPhoto() != null) {
            InputFile inputFile = new InputFile();
            inputFile.setMedia(update.getCallbackQuery().getMessage().getPhoto().get(0).getFileId());
            sendMessage.setPhoto(inputFile);
            sendMessage.setCaption(update.getCallbackQuery().getMessage().getCaption());
            sendPhoto(sendMessage);
            b = true;
        }
        else if (callData.equals(BotCallbacks.ACCEPT.getCallbackData())) {
            SendMessage sendMessageWithoutPhoto = new SendMessage();
            sendMessageWithoutPhoto.setChatId(groupService.getChannel(tag));
            sendMessageWithoutPhoto.setText(update.getCallbackQuery().getMessage().getText());
            sendMessage(sendMessageWithoutPhoto);

        }

        EditMessageCaption editMessageText = EditMessageCaption.builder()
                .chatId(chatId)
                .messageId(messageId)
                .caption(answer).parseMode("MarkdownV2").build();
        EditMessageText editMessageTextWithoutPhoto = EditMessageText.builder()
                .chatId(chatId)
                .messageId(messageId)
                .text(answer).parseMode("MarkdownV2").build();
        if (b)
            editMessage(editMessageText);
        else
            editMessageWithoutPhoto(editMessageTextWithoutPhoto);
    }

    private void editMessage(EditMessageCaption editMessageText) {
        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private void editMessageWithoutPhoto(EditMessageText editMessageText) {
        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    private void sendPhoto(SendPhoto sendPhoto) {
        SendChatAction sendChatAction = SendChatAction.builder()
                .chatId(sendPhoto.getChatId())
                .action(ActionType.TYPING.toString())
                .build();

        try {
            execute(sendPhoto);
            execute(sendChatAction);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void createMessageToAdminsApprove(Update update) {
        List<Long> admins = postCreatorService.generatePost(update.getMessage());
        KeyBoardHandler keyBoardHandler = new KeyBoardHandler();

        for (var admin : admins) {
            if (update.getMessage().getText() != null) {
                SendMessage sendMessage = SendMessage.builder()
                        .text(update.getMessage().getText())
                        .chatId(admin)
                        .build();
                sendMessage = keyBoardHandler.createInlineKeyBoard(sendMessage);
                sendMessage(sendMessage);
            } else {
                InputFile inputFile = new InputFile();
                inputFile.setMedia(update.getMessage().getPhoto().get(0).getFileId());
                SendPhoto msg = new SendPhoto();
                msg.setChatId(admin);
                msg.setPhoto(inputFile);
                msg.setCaption(update.getMessage().getCaption());
                msg = keyBoardHandler.createInlineKeyBoardForPhoto(msg);
                sendPhoto(msg);
            }
        }
    }

    private String getReactionOfReject(String answer) {
        return "~" + answer + "~";
    }

    private void findAdminsAfterAddGroup(Chat chat) {
        List<ChatMember> admins = new ArrayList<>();
        var id = chat.getId();
        try {
            admins = execute(new GetChatAdministrators(String.valueOf(id)));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        List<org.telegram.telegrambots.meta.api.objects.User> adminUsers = admins.stream()
                .map(ChatMember::getUser).
                filter(adm -> !adm.getIsBot()).toList();
        userTelegramService.createAdmins(adminUsers, chat.getId());


    }

    private void validateChatMembers(Update update) {
        var myChatMember = update.getMyChatMember();
        var chatMember = myChatMember.getNewChatMember();
        var user = chatMember.getUser();
        if (user.getIsBot()) {
            doWithGroup(update);
        }
    }

    private void doWithGroup(Update update) {
        var myChatMember = update.getMyChatMember();
        var chatMember = myChatMember.getNewChatMember();
        var chat = myChatMember.getChat();

        if (!chat.getType().equals("channel"))
            return;

        if (chatMember instanceof ChatMemberAdministrator) {
            groupService.addChannel(chat);
            findAdminsAfterAddGroup(chat);
        }

        if (chatMember instanceof ChatMemberLeft || chatMember instanceof ChatMemberBanned) {
            userTelegramService.deleteAdminsAndChannel(chat);
        }

    }

    private void checkUserIsAdminOrBanned(Update update) {
        var myChatMember = update.getMyChatMember();
        var newChatMember = myChatMember.getNewChatMember();
        if (newChatMember instanceof ChatMemberAdministrator) {
            userTelegramService.createAdmin(myChatMember);
        }
    }
}
