package lana.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lana.common.BaseEntity;
import lana.post.Post;
import lana.preferences.Preference;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "core_user")
@Table(schema = "public", catalog = "lana")
public class User extends BaseEntity<UUID> {
    @Column(name = "telegram_id", nullable = false)
    private Long telegramId;
    @OneToMany(mappedBy = "author")
    private List<Post> posts;
    @OneToMany(mappedBy = "user")
    private List<Preference> preferences;

    @Override
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID uuid) {
        id = uuid;
    }
}
