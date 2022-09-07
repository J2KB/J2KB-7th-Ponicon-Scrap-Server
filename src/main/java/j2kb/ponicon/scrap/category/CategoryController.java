package j2kb.ponicon.scrap.category;

import j2kb.ponicon.scrap.category.dto.PostCategoryReq;
import j2kb.ponicon.scrap.domain.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    // @Autowired 보다는 생성자 주입 방식으로 DI를 진행했습니다.
    private final CategoryService categoryService;

    @PostMapping("")
    public ResponseEntity<?> categorySave(@RequestBody PostCategoryReq categoryDto, @RequestParam("id") Long userId) {
        categoryService.categorySave(categoryDto, userId);
        return null;
    }

    // 1. 일단 로그인한 user의 user.getId를 받아서 그에 맞는 카테고리를 가져와야한다.
    @GetMapping("/all")
    public List<Category> categoryListByUser(@RequestParam("id")Long userId) {
        return categoryService.categories(userId);
    }
}
