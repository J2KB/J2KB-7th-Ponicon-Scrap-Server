package j2kb.ponicon.scrap.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static j2kb.ponicon.scrap.response.validationSequence.ValidationGroup.NotNullGroup;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PutLinkReq {

    @NotBlank
    @Size(min = 2, max = 100)
    private String title; // 자료 제목
}
