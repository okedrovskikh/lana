package lana.handlers;

import lana.properties.BotCallbacks;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class KeyBoardHandler {
    public SendMessage createInlineKeyBoard(SendMessage message) {

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

        InlineKeyboardButton inlineKeyboardButton1 = generateButton(BotCallbacks.ACCEPT);
        InlineKeyboardButton inlineKeyboardButton2 = generateButton(BotCallbacks.REJECT);

        List<InlineKeyboardButton> rowInline1 = List.of(
                inlineKeyboardButton1,
                inlineKeyboardButton2);

        List<List<InlineKeyboardButton>> rowsInline = List.of(rowInline1);
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);

        return message;
    }

    private InlineKeyboardButton generateButton(BotCallbacks callback) {
        return InlineKeyboardButton.builder()
                .text(callback.getCallbackText())
                .callbackData(callback.getCallbackData())
                .build();
    }
}
