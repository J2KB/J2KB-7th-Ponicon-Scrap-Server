package j2kb.ponicon.scrap.category.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class CategoryListRes {
    private Long categoryId;
    private String name;
    private int numOfLink; // 일단 보류
    private int order;

    @Builder
    public CategoryListRes(Long categoryId, String name, int order) {
        this.categoryId = categoryId;
        this.name = name;
        this.order = order;
    }
}
