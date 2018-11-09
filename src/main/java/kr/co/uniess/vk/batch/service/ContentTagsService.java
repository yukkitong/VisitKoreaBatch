package kr.co.uniess.vk.batch.service;

import kr.co.uniess.vk.batch.repository.ContentTagsMapper;
import kr.co.uniess.vk.batch.repository.model.ContentTagsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContentTagsService {
    @Autowired
    private ContentTagsMapper contentTagsMapper;

    public String findOne(String cotid, String tagid) {
        return contentTagsMapper.findOne(cotid, tagid);
    }

    public int insert(ContentTagsVO item) {
        return contentTagsMapper.insert(item);
    }
}
