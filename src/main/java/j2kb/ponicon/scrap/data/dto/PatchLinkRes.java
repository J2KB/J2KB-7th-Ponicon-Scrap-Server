package j2kb.ponicon.scrap.data.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class PatchLinkRes {

    private Long linkId;
    private Long categoryId;
    private String url;
    private String title;
    private String imgUrl;
    private String domain;


}
