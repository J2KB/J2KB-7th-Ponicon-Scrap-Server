package j2kb.ponicon.scrap.data;

import j2kb.ponicon.scrap.domain.Category;
import j2kb.ponicon.scrap.domain.Link;
import j2kb.ponicon.scrap.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LinkDto {
    private String title;
    private String link;
    private String imgUrl;
    private User user;
    private Category category;
    public Link toEntity() {
        return Link.builder().title(title).link(link).imgUrl(imgUrl).user(user).category(category).build();
    }
}
