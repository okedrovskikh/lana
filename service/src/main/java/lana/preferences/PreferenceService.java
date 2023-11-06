package lana.preferences;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PreferenceService {
    private final PreferenceRepository repository;

    public void createPreference(Preference preference) {
        repository.save(preference);
    }

    // че за айди ты хочешь тут использовать
    // если это id полльзователя то просто найти пользователя и загрузи из него преференсы
    // если ресурс то надо искать по ресурсу
    // ну или можно написать native query
    // @Query(native = true, value = "select * from preferences where resourceId = :resourceId")
    public List<Preference> findByTelegramId(Long id) {
        return repository.findByUserTelegramId(id);
    }

    public void deletePreferences(UUID id) {
        repository.deletePreferences(id);
    }

    public List<Preference> findPreferenceByResourceId(UUID resource_id) {
        return repository.findPreferences(resource_id);
    }
}
