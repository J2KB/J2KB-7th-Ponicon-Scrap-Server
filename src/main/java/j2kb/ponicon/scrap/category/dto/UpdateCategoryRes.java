package j2kb.ponicon.scrap.category.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateCategoryRes {
    private Long categoryId;

    @Builder
    public UpdateCategoryRes(Long categoryId) {
        this.categoryId = categoryId;
    }
}
