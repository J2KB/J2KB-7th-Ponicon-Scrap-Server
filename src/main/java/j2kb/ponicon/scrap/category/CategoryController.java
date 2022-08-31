package j2kb.ponicon.scrap.category;

import j2kb.ponicon.scrap.data.ResponseDto;
import j2kb.ponicon.scrap.domain.Category;
import j2kb.ponicon.scrap.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    // @Autowired 보다는 생성자 주입 방식으로 DI를 진행했습니다.
    private final CategoryService categoryService;

    @PostMapping("/category")
    public ResponseDto<Integer> categorySave(@RequestBody CategoryDto categoryDto, User user) {
        categoryService.categorySave(categoryDto, user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

//    @GetMapping("/category/all")
//    public List<CategoryDto> list(@RequestBody Category category) {
//        return categoryService.categories();
//    }
    @GetMapping("/category/all")
    public List<Category> list(User user) {
        return categoryService.categories(user);
    }

}
