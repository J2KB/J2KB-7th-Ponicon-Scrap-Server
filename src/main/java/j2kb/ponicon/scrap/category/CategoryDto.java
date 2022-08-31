package j2kb.ponicon.scrap.category;

import j2kb.ponicon.scrap.domain.Category;
import j2kb.ponicon.scrap.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDto {
    private String name;
    private User user;
    public Category toEntity() {
        return Category.builder().name(name).user(user).build();
    }
}
