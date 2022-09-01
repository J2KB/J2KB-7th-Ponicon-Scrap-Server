package j2kb.ponicon.scrap.category;

import j2kb.ponicon.scrap.domain.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public int categorySave(CategoryDto categoryDto, UserDto userDto) {
        try {
            categoryDto.setUser(userDto.toEntity());
            categoryRepository.save(categoryDto.toEntity());
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("categoryService ={} ", e.getMessage());
        }
        return -1;
    }

    @Transactional(readOnly = true)
    public List<Category> categories(UserDto userDto) {
        return  categoryRepository.findByUser(userDto.toEntity());
    }
}
