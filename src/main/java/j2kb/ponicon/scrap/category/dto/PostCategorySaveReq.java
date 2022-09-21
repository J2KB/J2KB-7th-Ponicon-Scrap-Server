package j2kb.ponicon.scrap.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostCategorySaveReq {

    @NotBlank
    @Size(min = 2, max = 60)
    private String name; // 카테고리 이름
}
