package j2kb.ponicon.scrap.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDataReq {

    private Long linkId;
    private Long categoryId;
}
