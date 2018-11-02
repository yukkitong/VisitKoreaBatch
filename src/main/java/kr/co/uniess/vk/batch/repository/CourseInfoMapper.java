package kr.co.uniess.vk.batch.repository;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CourseInfoMapper {
    int insertList(List<Map<String, Object>> list);
    int update(Map<String, Object> item);
    int delete(String cotId);
}
