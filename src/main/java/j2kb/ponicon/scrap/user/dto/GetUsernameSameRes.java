package j2kb.ponicon.scrap.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetUsernameSameRes {

    @JsonProperty("isDuplicate")
    private boolean duplicate; // true = 중복, false = 중복x

}
