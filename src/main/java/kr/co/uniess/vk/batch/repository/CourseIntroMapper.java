package kr.co.uniess.vk.batch.repository;

import kr.co.uniess.vk.batch.repository.model.CourseIntroVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseIntroMapper {
    int insert(CourseIntroVO item);
    int update(CourseIntroVO item);
    int delete(String cotid);
}
