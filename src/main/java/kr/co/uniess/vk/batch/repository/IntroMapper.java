package kr.co.uniess.vk.batch.repository;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface IntroMapper {
    int insertTouristIntro(Map<String, Object> item);
    int updateTouristIntro(Map<String, Object> item);
    int insertCulturalIntro(Map<String, Object> item);
    int updateCulturalIntro(Map<String, Object> item);
    int insertFestivalIntro(Map<String, Object> item);
    int updateFestivalIntro(Map<String, Object> item);
    int insertCourseIntro(Map<String, Object> item);
    int updateCourseIntro(Map<String, Object> item);
    int insertLeportsIntro(Map<String, Object> item);
    int updateLeportsIntro(Map<String, Object> item);
    int insertAccommodationIntro(Map<String, Object> item);
    int updateAccommodationIntro(Map<String, Object> item);
    int insertShoppingIntro(Map<String, Object> item);
    int updateShoppingIntro(Map<String, Object> item);
    int insertEateryIntro(Map<String, Object> item);
    int updateEateryIntro(Map<String, Object> item);
}
