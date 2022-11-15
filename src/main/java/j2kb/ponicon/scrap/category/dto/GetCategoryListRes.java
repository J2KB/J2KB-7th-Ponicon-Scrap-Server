package j2kb.ponicon.scrap.category.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor // cache cannot deserialize from Object value 오류 때문에 추가함
public class GetCategoryListRes {
    List<CategoryListRes> categories;

    @Builder
    public GetCategoryListRes(List<CategoryListRes> categories) {
        this.categories = categories;
    }
}
