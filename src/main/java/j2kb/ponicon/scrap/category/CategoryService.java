package j2kb.ponicon.scrap.category;

import j2kb.ponicon.scrap.category.dto.*;
import j2kb.ponicon.scrap.domain.User;

public interface CategoryService {
    public PostCategorySaveRes categorySave(PostCategorySaveReq postCategoryReq, Long userId);

    public GetCategoryListRes categories(Long userId);

    public void saveBasicCategory(User user);

    public DeleteCategoryRes categoryDelete(Long categoryId);

    public UpdateCategoryRes updateCategory(UpdateCategoryReq updateCategoryReq, Long categoryId);
}
