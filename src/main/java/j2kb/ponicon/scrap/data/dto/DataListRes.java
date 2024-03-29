package j2kb.ponicon.scrap.data.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class DataListRes {
    private Long linkId;
    private String link;
    private String title;
    private String domain;
    private String imgUrl;
    private boolean isBookmark;

    @Builder
    public DataListRes(Long linkId, String link, String title, String domain, String imgUrl, boolean isBookmark) {
        this.linkId = linkId;
        this.link = link;
        this.title = title;
        this.domain = domain;
        this.imgUrl = imgUrl;
        this.isBookmark = isBookmark;
    }
}
