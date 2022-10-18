package j2kb.ponicon.scrap.category.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeleteCategoryRes {
    private Long categoryId;

    @Builder
    public DeleteCategoryRes(Long categoryId) {
        this.categoryId = categoryId;
    }
}
