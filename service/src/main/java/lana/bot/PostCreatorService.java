package lana.bot;

import lana.channel.Channel;
import lana.post.Post;
import lana.post.PostPayload;
import lana.user.User;
import org.springframework.stereotype.Component;

@Component
public class PostCreatorService {
    public Post generatePost(Long telegramUserID, Long telegramChannelId, String text, Byte[] binaryData) {
        Post post = new Post();

        PostPayload postPayload = new PostPayload();
        postPayload.setBinaryData(binaryData);
        postPayload.setText(text);

        post.setPayload(postPayload);

        Channel channel = new Channel();
        channel.setTelegramId(telegramChannelId);

        post.setChannel(channel);
        User user = new User();
        user.setTelegramId(telegramUserID);
        post.setAuthor(user);
        return post;
    }
}
