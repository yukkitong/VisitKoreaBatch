package kr.co.uniess.vk.batch.repository;

import kr.co.uniess.vk.batch.repository.model.EateryIntroVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EateryIntroMapper {
    int insert(EateryIntroVO item);
    int update(EateryIntroVO item);
    int delete(String cotId);
}
