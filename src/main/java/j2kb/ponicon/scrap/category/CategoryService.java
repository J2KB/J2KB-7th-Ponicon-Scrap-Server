package j2kb.ponicon.scrap.category;

import j2kb.ponicon.scrap.category.dto.GetCategoryListRes;
import j2kb.ponicon.scrap.category.dto.PostCategorySaveReq;
import j2kb.ponicon.scrap.category.dto.PostCategorySaveRes;
import j2kb.ponicon.scrap.domain.Category;
import j2kb.ponicon.scrap.category.dto.*;
import j2kb.ponicon.scrap.domain.User;

public interface CategoryService {
    // 카테고리 저장
    public PostCategorySaveRes categorySave(PostCategorySaveReq postCategoryReq, Long userId);
    // 모든 카테고리 조회
    public GetCategoryListRes categories(Long userId);
    // 기본 카테고리 저장
    public void saveBasicCategory(User user);
    // 카테고리 하나 조회
    public Category findCategoryOne(Long categoryId);
    // 카테고리 삭제
    public void categoryDelete(Long categoryId);
    // 카테고리 수정
    public UpdateCategoryRes updateCategory(UpdateCategoryReq updateCategoryReq, Long categoryId);
    // 카테고리 순서 수정
    public void updateIdxCategory(UpdateIdxCategoryReq updateIdxCategoryReq, Long userId);

}
