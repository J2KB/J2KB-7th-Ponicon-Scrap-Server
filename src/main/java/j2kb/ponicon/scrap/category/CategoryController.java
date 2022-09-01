package j2kb.ponicon.scrap.category;

import j2kb.ponicon.scrap.data.ResponseDto;
import j2kb.ponicon.scrap.domain.Category;
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
    public ResponseDto<Integer> categorySave(@RequestBody CategoryDto categoryDto, UserDto userDto) {
        userDto.setId(2L);
        categoryService.categorySave(categoryDto, userDto);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    // 1. 일단 로그인한 user의 user.getId를 받아서 그에 맞는 카테고리를 가져와야한다.
    @GetMapping("/category/all")
    public List<Category> categoryListByUser(UserDto userDto) {
        userDto.setId(2L);
        return categoryService.categories(userDto);
    }
}
