package j2kb.ponicon.scrap.data.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
public class PutLinkRes {

    private Long linkId;

    @Builder
    public PutLinkRes(Long linkId) {
        this.linkId = linkId;
    }
}
