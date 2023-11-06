package lana.bot;

import lana.channel.Channel;
import lana.channel.ChannelService;
import lana.preferences.Action;
import lana.preferences.Preference;
import lana.preferences.PreferenceService;
import lana.user.User;
import lana.user.UserCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.ChatMemberUpdated;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserTelegramService {
    private final PreferenceService preferenceService;
    private final lana.user.UserService userService;
    private final ChannelService channelService;

    @Transactional
    public void createAdmin(ChatMemberUpdated chatMemberUpdated) {

        var userOptional = userService.findById(chatMemberUpdated.getNewChatMember().getUser().getId());
        User user;
        if(userOptional.isEmpty()) {
            UserCreateDto userCreateDto = new UserCreateDto();
            userCreateDto.setTelegramId(chatMemberUpdated.getNewChatMember().getUser().getId());
            user = userService.create(userCreateDto);
        } else {
            user = userOptional.get();
        }
        Preference preference = new Preference();
        preference.setUser(user);
        preference.setResourceId(channelService.getByTelegramId(chatMemberUpdated.getChat().getId()).getId());
        preference.setAction(Action.ADMIN);
        preferenceService.createPreference(preference);
    }
    @Transactional
    public void createAdmin(org.telegram.telegrambots.meta.api.objects.User userTG, Long chatId) {

        var userOptional = userService.findById(userTG.getId());
        User user;
        if(userOptional.isEmpty()) {
            UserCreateDto userCreateDto = new UserCreateDto();
            userCreateDto.setTelegramId(userTG.getId());
            user = userService.create(userCreateDto);
        } else {
            user = userOptional.get();
        }
        Preference preference = new Preference();
        preference.setUser(user);
        preference.setResourceId(channelService.getByTelegramId(chatId).getId());
        preference.setAction(Action.ADMIN);
        preferenceService.createPreference(preference);
    }
    @Transactional
    public void createAdmins(List<org.telegram.telegrambots.meta.api.objects.User> admins, Long chatId) {
        for(var adm : admins) {
            createAdmin(adm, chatId);
        }
    }
    //TODO: сделать удаление админов
    @Transactional
    public void deleteAdminsAndChannel(Chat chat) {
        Channel chan = channelService.getByTelegramId(chat.getId());
        System.out.println(chan.getId());
        preferenceService.deletePreferences(chan.getId());
        channelService.delete(chan);
    }
}
