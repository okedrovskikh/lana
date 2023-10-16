package lana.bot;


import lombok.Getter;

@Getter
public enum BotCallbacks {
    ACCEPT("Accept", "ACCEPTED"),
    REJECT("Reject", "REJECTED"),
    ;

    private final String callbackData;
    private final String callbackText;


    BotCallbacks(String callbackText, String callbackData) {
        this.callbackData = callbackData;
        this.callbackText = callbackText;
    }

}
