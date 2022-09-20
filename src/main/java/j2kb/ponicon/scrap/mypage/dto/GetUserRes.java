package j2kb.ponicon.scrap.mypage.dto;

import j2kb.ponicon.scrap.domain.User;
import lombok.Getter;


@Getter
public class GetUserRes {
    private String name;
    private String username;

    // entity를 dto로 변환
    public GetUserRes(User entity) {
        this.name = entity.getName();
        this.username = entity.getUsername();
    }
}
