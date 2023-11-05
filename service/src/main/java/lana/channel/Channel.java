package lana.channel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lana.common.BaseEntity;
import lana.post.Post;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "channel")
@Table(schema = "public", catalog = "lana")
public class Channel extends BaseEntity<UUID> {
    @Column(name = "telegram_id", nullable = false)
    private Long telegramId;
    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL)
    private List<Post> posts;

    @Override
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID uuid) {
        id = uuid;
    }
}
