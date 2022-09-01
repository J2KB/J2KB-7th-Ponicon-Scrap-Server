package j2kb.ponicon.scrap.data;

import j2kb.ponicon.scrap.category.CategoryDto;
import j2kb.ponicon.scrap.category.UserDto;
import j2kb.ponicon.scrap.domain.Link;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LinkService {

    private final LinkRepository linkRepository;

    @Transactional
    public int save(LinkDto linkDto, UserDto userDto, CategoryDto categoryDto) {
        try {
            linkDto.setUser(userDto.toEntity());
            linkDto.setCategory(categoryDto.toEntity());
            linkRepository.save(linkDto.toEntity());
            return 1;
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error("linkService ={} ", e.getMessage());
        }
        return -1;
    }

    @Transactional(readOnly = true)
    public List<Link> links(UserDto userDto, CategoryDto categoryDto) {
        return  linkRepository.findByUserAndCategory(userDto.toEntity(), categoryDto.toEntity());
    }
}
