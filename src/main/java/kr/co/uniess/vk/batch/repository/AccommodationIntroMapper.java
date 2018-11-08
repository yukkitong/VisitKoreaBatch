package kr.co.uniess.vk.batch.repository;

import kr.co.uniess.vk.batch.repository.model.AccommodationIntroVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccommodationIntroMapper {
    int insert(AccommodationIntroVO item);
    int update(AccommodationIntroVO item);
    int delete(String cotid);
}
