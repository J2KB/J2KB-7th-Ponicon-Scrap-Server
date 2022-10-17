package j2kb.ponicon.scrap.data.dto;

import j2kb.ponicon.scrap.response.validationSequence.ValidationGroup;
import j2kb.ponicon.scrap.response.validationSequence.ValidationGroup.NotEmptyGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

import static j2kb.ponicon.scrap.response.validationSequence.ValidationGroup.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PatchLinkReq {

    @NotNull(message = "카테고리 인덱스는 필수값 입니다", groups = NotNullGroup.class)
    private Long categoryId;
}
