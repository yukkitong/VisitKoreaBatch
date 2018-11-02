package kr.co.uniess.vk.batch.repository;

import kr.co.uniess.vk.batch.repository.model.LeportsIntroVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LeportsIntroMapper {
    int insert(LeportsIntroVO item);
    int update(LeportsIntroVO item);
    int delete(String cotId);
}
