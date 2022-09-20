package j2kb.ponicon.scrap.data.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GetDataListRes {
    List<DataListRes> links;

    @Builder
    public GetDataListRes(List<DataListRes> links) {
        this.links = links;
    }
}
