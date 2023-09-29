package lana.Bot.properties;


import lombok.Getter;

@Getter
public enum BotCallbacks {
    ACCEPT("Accept", "ACCEPTED"),
    REJECT("Reject", "REJECTED"),
    ;

    private final String callbackData;
    private final String callbackText;


    BotCallbacks(String callbackData, String callbackText) {
        this.callbackData = callbackData;
        this.callbackText = callbackText;
    }

}
