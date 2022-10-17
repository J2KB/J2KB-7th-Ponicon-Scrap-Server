package j2kb.ponicon.scrap.category;

import j2kb.ponicon.scrap.category.dto.GetCategoryListRes;
import j2kb.ponicon.scrap.category.dto.PostCategorySaveReq;
import j2kb.ponicon.scrap.category.dto.PostCategorySaveRes;
import j2kb.ponicon.scrap.domain.Category;
import j2kb.ponicon.scrap.domain.User;

public interface CategoryService {
    public PostCategorySaveRes categorySave(PostCategorySaveReq postCategoryReq, Long userId);

    public GetCategoryListRes categories(Long userId);

    public void saveBasicCategory(User user);

    public Category findCategoryOne(Long categoryId);
}
