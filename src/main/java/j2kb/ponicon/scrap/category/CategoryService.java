package j2kb.ponicon.scrap.category;

import j2kb.ponicon.scrap.domain.Category;
import j2kb.ponicon.scrap.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public void categorySave(CategoryDto categoryDto, User user) {
        categoryDto.setUser(user);
        categoryRepository.save(categoryDto.toEntity());
    }

//    @org.springframework.transaction.annotation.Transactional(readOnly = true)
//    public List<CategoryDto> categories() {
//        List<Category> list = categoryRepository.findAll();
//        return list.stream().map(CategoryDto::new).collect(Collectors.toList());
//    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<Category> categories(User user) {
        return  categoryRepository.findAllById(Collections.singleton(user.getId()));

    }
}
