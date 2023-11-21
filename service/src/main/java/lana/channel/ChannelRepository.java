package lana.channel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ChannelRepository extends JpaRepository<Channel, UUID> {
    Long deleteChannelsByTelegramId(Long id);
    Optional<Channel> findByTelegramId(Long id);

    Channel findByTag(String tag);
}
