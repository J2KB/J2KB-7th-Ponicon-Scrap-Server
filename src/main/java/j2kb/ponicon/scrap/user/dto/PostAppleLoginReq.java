package j2kb.ponicon.scrap.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostAppleLoginReq {
    private String userIdentifier;
    private String name;
    private String email;
}
