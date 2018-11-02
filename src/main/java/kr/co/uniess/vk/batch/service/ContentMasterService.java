package kr.co.uniess.vk.batch.service;


import kr.co.uniess.vk.batch.repository.ContentMasterMapper;
import kr.co.uniess.vk.batch.repository.model.ContentMasterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContentMasterService {

    @Autowired
    private ContentMasterMapper contentMasterMapper;

    public int getTotalCount() {
        return contentMasterMapper.getTotalCount();
    }

    public String findOne(String contentId) {
        return contentMasterMapper.findOne(contentId);
    }

    public int insert(ContentMasterVO item) {
        return contentMasterMapper.insert(item);
    }

    public int update(ContentMasterVO item) {
        return contentMasterMapper.update(item);
    }
}
