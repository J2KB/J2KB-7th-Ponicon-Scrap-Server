package j2kb.ponicon.scrap.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostLoginReq {

    private String username; // 아이디
    private String password; // 비번
    private boolean autoLogin; // 자동 로그인 여부
}
