package j2kb.ponicon.scrap.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDataSaveReq {
    private String link;
    private String title;
    private String imgUrl;
}