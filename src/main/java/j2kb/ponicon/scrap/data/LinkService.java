package j2kb.ponicon.scrap.data;

import j2kb.ponicon.scrap.data.dto.GetDataListRes;
import j2kb.ponicon.scrap.data.dto.PostDataSaveRes;
import j2kb.ponicon.scrap.data.dto.PostUrlReq;

public interface LinkService {
    public PostDataSaveRes linkSave(PostUrlReq postUrlReq, Long userId, Long categoryId) throws Exception;

    public GetDataListRes links(Long userId, Long categoryId, String seq);

    public GetDataListRes allLinks(Long userId);

    public void deleteLink(Long userId, Long linkId);
}
