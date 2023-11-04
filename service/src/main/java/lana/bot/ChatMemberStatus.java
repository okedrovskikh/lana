package lana.bot;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ChatMemberStatus {
    KICKED("kick"),
    ADMIN("administrator")
    ;


    private String status;
}
