package lana.Bot.Client;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class BotClient {
    private final WebClient webClient;

    public WebClient webClient() {
        return webClient;
    }
    public String abc () {
        return webClient.get().uri("/api")
                .retrieve().bodyToMono(String.class).block();
    }
}
