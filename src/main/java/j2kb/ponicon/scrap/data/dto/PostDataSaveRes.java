package j2kb.ponicon.scrap.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDataSaveRes {
    private Long linkId;

    @Builder
    public PostDataSaveRes(Long linkId) {
        this.linkId = linkId;
    }
}
