package j2kb.ponicon.scrap.category.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GetCategoryListRes {
    List<CategoryListRes> categories;

    @Builder
    public GetCategoryListRes(List<CategoryListRes> categories) {
        this.categories = categories;
    }
}
