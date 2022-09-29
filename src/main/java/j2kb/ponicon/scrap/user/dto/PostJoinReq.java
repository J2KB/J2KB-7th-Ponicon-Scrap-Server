package j2kb.ponicon.scrap.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostJoinReq {

    @NotEmpty(message = "아이디를 입력해주세요")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{5,15}$", message = "아이디 형식이 일치하지 않습니다")
    private String username; // 아이디

    @NotEmpty(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d!?@#$%&*]{5,15}$", message = "비밀번호 형식이 일치하지 않습니다")
    private String password; // 비밀번호

    @NotEmpty(message = "이름을 입력해주세요")
    @Pattern(regexp = "^(?=.*[a-zA-Z가-힣])[A-Za-z가-힣]{1,30}$", message = "이름 형식이 일치하지 않습니다")
    private String name; // 이름
}
