package j2kb.ponicon.scrap.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostLoginReq {

    @NotEmpty(message = "아이디는 필수값입니다")
    private String username; // 아이디

    @NotEmpty(message = "비밀번호는 필수값입니다")
    private String password; // 비번

    @NotNull(message = "자동 로그인 여부는 필수값입니다")
    private Boolean autoLogin; // 자동 로그인 여부
}
