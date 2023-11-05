package lana.preferences;

import jakarta.persistence.*;
import lana.common.BaseEntity;
import lana.user.User;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity(name = "preference")
@Table(schema = "public", catalog = "lana")
public class Preference extends BaseEntity<Long> {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "resource_id", nullable = false)
    private UUID resourceId;
    @Column(name = "action", nullable = false)
    @Enumerated(EnumType.STRING)
    private Action action;

    {resourceId = UUID.randomUUID();}
    @Override
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    @Override
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public void setId(Long aLong) {
        id = aLong;
    }
}