package kr.co.uniess.vk.batch.repository;

import kr.co.uniess.vk.batch.repository.model.FestivalIntroVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FestivalIntroMapper {
    int insert(FestivalIntroVO item);
    int update(FestivalIntroVO item);
    int delete(String cotid);
}
