package lana.preferences;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
    private String action;

    @Override
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long aLong) {
        id = aLong;
    }
}
