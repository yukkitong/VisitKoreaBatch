package kr.co.uniess.vk.batch.repository;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface CulturalIntroMapper {
    int insert(Map<String, Object> item);
    int update(Map<String, Object> item);
    int delete(String cotId);
}
