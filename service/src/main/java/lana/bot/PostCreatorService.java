package lana.bot;

import lana.channel.Channel;
import lana.channel.ChannelService;
import lana.post.Post;
import lana.post.PostPayload;
import lana.post.PostService;
import lana.preferences.Preference;
import lana.preferences.PreferenceService;
import lana.user.User;
import lana.user.UserCreateDto;
import lana.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PostCreatorService {
    private final PostService postService;
    private final ChannelService channelService;
    private final UserService userService;
    private final PreferenceService preferenceService;
    @Transactional
    public List<Long> generatePost(Message message) {
        Post post = new Post();
        Optional<User> userOpt = userService.findById(message.getChat().getId());
        User user;
        if(userOpt.isEmpty()) {
            UserCreateDto userCreateDto = new UserCreateDto();
            userCreateDto.setTelegramId(message.getChat().getId());
            user = userService.create(userCreateDto);
        } else {
            user = userOpt.get();
        }

        post.setAuthor(user);
        Channel channel;
        if(message.getEntities()!=null) {
            channel = channelService.getChannel(message.getEntities().get(0).getText());
        }
        else{
            channel = channelService.getChannel(message.getCaptionEntities().get(0).getText());
        }
        post.setChannel(channel);
        PostPayload postPayload = new PostPayload();
        postPayload.setText(message.getText());
        if (message.getPhoto() != null && message.getPhoto().size() != 0)
            postPayload.setFileId(message.getPhoto().get(0).getFileId());
        post.setPayload(postPayload);
        postService.create(post);
        List<Preference> p = preferenceService.findPreferenceByResourceId(channel.getId());
        List<Long> ids = new ArrayList<>();
        for(var pref : p) {
            ids.add(pref.getUser().getTelegramId());
        }
        return ids;
    }
}
