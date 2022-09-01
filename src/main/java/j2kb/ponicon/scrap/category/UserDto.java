package j2kb.ponicon.scrap.category;

import j2kb.ponicon.scrap.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String name;

    public User toEntity() {
        return User.builder().id(id).username(username).password(password).name(name).build();
    }
}
