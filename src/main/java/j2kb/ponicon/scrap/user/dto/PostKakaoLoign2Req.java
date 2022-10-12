package j2kb.ponicon.scrap.user.dto;

import j2kb.ponicon.scrap.response.validationSequence.ValidationGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

import static j2kb.ponicon.scrap.response.validationSequence.ValidationGroup.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostKakaoLoign2Req {

    @NotEmpty(message = "access토큰은 필수값 입니다", groups = NotNullGroup.class)
    private String accessToken;
    private String refreshToken;
}
