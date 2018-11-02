package kr.co.uniess.vk.batch.repository;

import kr.co.uniess.vk.batch.repository.model.DetailWithTourVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DetailWithTourMapper {
    int insert(DetailWithTourVO item);
    int deleteByContentId(String contentId);
    int deleteByCotId(String cotId);
}
