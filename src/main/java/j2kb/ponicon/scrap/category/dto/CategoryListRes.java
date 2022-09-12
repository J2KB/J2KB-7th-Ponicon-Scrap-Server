package j2kb.ponicon.scrap.category.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class CategoryListRes {
    private Long id;
    private String name;
    private int numOfLink; // 일단 보류
    private int order;

    @Builder
    public CategoryListRes(Long id, String name, int order) {
        this.id = id;
        this.name = name;
        this.order = order;
    }
}
