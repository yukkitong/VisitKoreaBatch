package kr.co.uniess.vk.batch.service;

import kr.co.uniess.vk.batch.component.model.Master;
import kr.co.uniess.vk.batch.repository.ContentMasterMapper;
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

    public int insert(Master master) {
        return contentMasterMapper.insert(master);
    }

    public int update(Master master) {
        return contentMasterMapper.update(master);
    }
}
