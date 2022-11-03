package j2kb.ponicon.scrap.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Getter
@NoArgsConstructor // cache cannot deserialize from Object value 오류 때문에 추가함
public class GetDataListRes {

    List<DataListRes> links;

    @Builder
    public GetDataListRes(List<DataListRes> links) {
        this.links = links;
    }
}
