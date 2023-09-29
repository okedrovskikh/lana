package lana.Bot.handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class KeyBoardHandler {
    public SendMessage createInlineKeyBoard(SendMessage message) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = generateButton("Accepted");
        InlineKeyboardButton inlineKeyboardButton2 = generateButton("Rejected");
        List<InlineKeyboardButton> rowInline1 = List.of(
                inlineKeyboardButton1,
                inlineKeyboardButton2);
        List<List<InlineKeyboardButton>> rowsInline = List.of(rowInline1);
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);
        return message;
    }

    private InlineKeyboardButton generateButton(String text) {
        return InlineKeyboardButton.builder()
                .text(text)
                .callbackData(text.toUpperCase())
                .build();

    }
}
