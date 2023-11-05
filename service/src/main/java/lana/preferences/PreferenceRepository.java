package lana.preferences;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {
    @Query(nativeQuery = true, value = "select p from lana_user join preferences as p on user_id = lana_user.id where telegram_id = :id")
    List<Preference> findByUserTelegramId(Long id);
    void deletePreferenceById(Long id);
}
