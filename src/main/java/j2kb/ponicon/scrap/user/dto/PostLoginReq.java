package j2kb.ponicon.scrap.user.dto;

import j2kb.ponicon.scrap.response.validationSequence.ValidationGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static j2kb.ponicon.scrap.response.validationSequence.ValidationGroup.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostLoginReq {

    @NotEmpty(message = "아이디는 필수값입니다", groups = NotEmptyGroup.class)
    private String email; // 아이디

    @NotEmpty(message = "비밀번호는 필수값입니다", groups = NotEmptyGroup.class)
    private String password; // 비번

    @NotNull(message = "자동 로그인 여부는 필수값입니다", groups = NotEmptyGroup.class)
    private Boolean autoLogin; // 자동 로그인 여부
}
