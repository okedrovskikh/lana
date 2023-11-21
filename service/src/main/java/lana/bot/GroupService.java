package lana.bot;

import lana.channel.Channel;
import lana.channel.ChannelCreateDto;
import lana.channel.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;

@Component
@RequiredArgsConstructor
public class GroupService {
    private final ChannelService channelService;


    public void addChannel(Chat chat) {
        var channelDTO = new ChannelCreateDto();
        channelDTO.setChatID(chat.getId());
        channelDTO.setTag('@'+ chat.getUserName());
        channelService.create(channelDTO);
    }
    public void deleteChannel(Chat chat){
        channelService.deleteByTelegramId(chat.getId());
    }
    public void deleteChannel(Channel chat){
        channelService.delete(chat);
    }
    public Long getChannel(String tag) {
        return channelService.getChannel(tag).getTelegramId();
    }
}
