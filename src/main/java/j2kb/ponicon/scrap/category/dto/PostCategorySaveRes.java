package j2kb.ponicon.scrap.category.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class PostCategorySaveRes {
    private Long categoryId;

    @Builder
    public PostCategorySaveRes(Long categoryId) {
        this.categoryId = categoryId;
    }
}
