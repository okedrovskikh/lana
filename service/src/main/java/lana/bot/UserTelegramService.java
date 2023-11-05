package lana.bot;

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
        preference.setId(1L);
        preferenceService.createPreference(preference);
    }
    private static Long l = 1L;
    @Transactional
    public void createAdmin(org.telegram.telegrambots.meta.api.objects.User userTG, Long channelId) {

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
        preference.setResourceId(channelService.getByTelegramId(channelId).getId());
        preference.setAction(Action.ADMIN);
        preference.setId(l);
        l++;
        preferenceService.createPreference(preference);
    }
    //TODO: сделать удаление админов
//    @Transactional
//    public void deleteAdmin(org.telegram.telegrambots.meta.api.objects.User userTG, Long channelId) {
//
//        Optional<Preference> preferenceOpt = preferenceService.findByTelegramId(channelId);
//        preferenceService.createPreference(preference);
//    }
}
