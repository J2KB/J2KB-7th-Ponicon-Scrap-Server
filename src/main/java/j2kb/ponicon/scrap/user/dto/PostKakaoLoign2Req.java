package j2kb.ponicon.scrap.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostKakaoLoign2Req {

    @NotEmpty(message = "access토큰은 필수값 입니다")
    private String accessToken;
    private String refreshToken;
}
