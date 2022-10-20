package j2kb.ponicon.scrap.category.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class CategoryListRes {
    private Long categoryId;
    private String name;
    private int numOfLink;
    private int order;

    @Builder
    public CategoryListRes(Long categoryId, String name, int numOfLink, int order) {
        this.categoryId = categoryId;
        this.name = name;
        this.numOfLink = numOfLink;
        this.order = order;
    }
}
