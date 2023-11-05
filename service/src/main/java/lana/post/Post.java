package lana.post;

import jakarta.persistence.*;
import lana.channel.Channel;
import lana.common.BaseEntity;
import lana.user.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Getter
@Setter
@Entity(name = "post")
@Table(schema = "public", catalog = "lana")
public class Post extends BaseEntity<UUID> {
    @ManyToOne()
    @JoinColumn(name = "chat_id")
    private Channel channel;
    @ManyToOne()
    @JoinColumn(name = "author_id")
    private User author;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "payload", nullable = false, columnDefinition = "jsonb")
    private PostPayload payload;

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
