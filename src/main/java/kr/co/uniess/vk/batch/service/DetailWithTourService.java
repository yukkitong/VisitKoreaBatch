package kr.co.uniess.vk.batch.service;

import kr.co.uniess.vk.batch.model.DetailWithTour;
import kr.co.uniess.vk.batch.repository.DetailWithTourMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetailWithTourService {

    @Autowired
    private DetailWithTourMapper detailWithTourMapper;

    public int insert(DetailWithTour item) {
        return detailWithTourMapper.insert(item);
    }

    public int deleteByContentId(String contentId) {
        return detailWithTourMapper.deleteByContentId(contentId);
    }

    public int deleteByCotId(String cotId) {
        return detailWithTourMapper.deleteByCotId(cotId);
    }
}
