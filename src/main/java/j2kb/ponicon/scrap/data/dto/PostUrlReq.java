package j2kb.ponicon.scrap.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// 프론트 변경으로 현재 미사용 PostDataSaveReq로 변경
public class PostUrlReq {
    @NotBlank
    private String baseURL;
}
