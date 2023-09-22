package lana.Bot.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties("telegram.bot")
@Getter
public class BotProperties {

    private final String token;
    private final String name;

    @ConstructorBinding
    public BotProperties(String token, String name) {
        this.token = token;
        this.name = name;
    }
}


