package lana.preferences;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PreferenceService {
    private final PreferenceRepository repository;

    @Transactional
    public void createPreference(Preference preference)  {
        repository.save(preference);
    }
    @Transactional
    public Optional<Preference> findByTelegramId(Long id)  {
        return repository.findByTelegramId(id);
    }
    @Transactional
    public void deletePreference(Preference preference) {
        repository.deletePreferenceById(preference.getId());
    }
}
