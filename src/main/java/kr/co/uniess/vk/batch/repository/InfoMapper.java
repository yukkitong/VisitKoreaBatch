package kr.co.uniess.vk.batch.repository;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface InfoMapper {
    int insertAccommodationInfoList(List<Map<String, Object>> list);
    int updateAccommodationInfo(Map<String, Object> item);

    int insertCourseInfoList(List<Map<String, Object>> list);
    int updateCourseInfo(Map<String, Object> item);

    int insertDetailInfoList(List<Map<String, Object>> list);
    int updateDetailInfo(Map<String, Object> item);
}
