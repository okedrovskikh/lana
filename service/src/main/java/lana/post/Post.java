package lana.post;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
    @ManyToOne(optional = false)
    @JoinColumn(name = "channel_id")
    private Channel channel;
    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id")
    private User author;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "payload", nullable = false, columnDefinition = "jsonb")
    private PostPayload payload;

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
