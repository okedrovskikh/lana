package lana.preferences;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {
    @Query(nativeQuery = true, value = "select p from lana_user join preference as p on user_id = lana_user.id where telegram_id = :id")
    List<Preference> findByUserTelegramId(Long id);
    @Query(nativeQuery = true, value = "delete from preference where action='ADMIN' and resource_id= :resource_id")
    @Modifying
    int deletePreferences(UUID resource_id);

    @Query(nativeQuery = true, value = "select * from preference where action='ADMIN' and resource_id= :resource_id")
    List<Preference> findPreferences(UUID resource_id);
}
