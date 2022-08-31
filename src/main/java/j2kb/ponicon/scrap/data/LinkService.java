package j2kb.ponicon.scrap.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;

    @Transactional
    public int save(LinkDto linkDto) {
        try {
            linkRepository.save(linkDto.toEntity());
            return 1;
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("linkService ={} " + e.getMessage());
        }
        return -1;
    }
}
