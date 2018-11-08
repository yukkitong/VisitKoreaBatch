package kr.co.uniess.vk.batch.repository;

import kr.co.uniess.vk.batch.repository.model.CourseInfoVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseInfoMapper {
    int insertList(List<CourseInfoVO> list);
    int insert(CourseInfoVO item);
    int update(CourseInfoVO item);
    int delete(String cotid);
}
