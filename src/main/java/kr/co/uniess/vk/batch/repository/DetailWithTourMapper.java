package kr.co.uniess.vk.batch.repository;

import kr.co.uniess.vk.batch.model.DetailWithTour;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DetailWithTourMapper {
    int insert(DetailWithTour item);
    int deleteByContentId(String contentId);
    int deleteByCotId(String cotId);
}
