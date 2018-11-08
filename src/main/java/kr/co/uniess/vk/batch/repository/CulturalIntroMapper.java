package kr.co.uniess.vk.batch.repository;

import kr.co.uniess.vk.batch.repository.model.CulturalIntroVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CulturalIntroMapper {
    int insert(CulturalIntroVO item);
    int update(CulturalIntroVO item);
    int delete(String cotid);
}
