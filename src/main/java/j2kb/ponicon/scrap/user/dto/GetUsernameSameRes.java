package j2kb.ponicon.scrap.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetUsernameSameRes {

    private boolean isDuplication; // true = 중복, false = 중복x
}
