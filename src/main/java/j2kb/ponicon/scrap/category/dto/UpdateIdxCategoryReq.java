package j2kb.ponicon.scrap.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateIdxCategoryReq {
    private int startIdx;
    private int endIdx;
}
