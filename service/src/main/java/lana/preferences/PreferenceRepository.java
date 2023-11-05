package lana.preferences;

import lana.channel.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {
    Optional<Preference> findByTelegramId(Long id);
    void deletePreferenceById(Long id);
}
