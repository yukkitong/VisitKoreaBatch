package kr.co.uniess.vk.batch.repository;

import kr.co.uniess.vk.batch.repository.model.TouristIntroVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TouristIntroMapper {
    int insert(TouristIntroVO item);
    int update(TouristIntroVO item);
    int delete(String cotId);
}
