package j2kb.ponicon.scrap.user.dto;

import j2kb.ponicon.scrap.response.validationSequence.ValidationGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import static j2kb.ponicon.scrap.response.validationSequence.ValidationGroup.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostJoinReq {

    @NotEmpty(message = "아이디를 입력해주세요", groups = NotEmptyGroup.class)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{5,15}$", message = "아이디 형식이 일치하지 않습니다", groups = PatternGroup.class)
    private String username; // 아이디

    @NotEmpty(message = "비밀번호를 입력해주세요", groups = NotEmptyGroup.class)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d!?@#$%&*]{5,15}$", message = "비밀번호 형식이 일치하지 않습니다", groups = PatternGroup.class)
    private String password; // 비밀번호

    @NotEmpty(message = "이름을 입력해주세요", groups = NotEmptyGroup.class)
    @Pattern(regexp = "^(?=. *[a-zA-Z가-힣])[A-Za-z가-힣]{1,30}$", message = "이름 형식이 일치하지 않습니다", groups = PatternGroup.class)
    private String name; // 이름
}
