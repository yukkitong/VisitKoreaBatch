package kr.co.uniess.vk.batch.service;

import kr.co.uniess.vk.batch.repository.IntroMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class IntroService {

    @Autowired
    private IntroMapper introMapper;

    public int insertTouristIntro(Map<String, Object> item) {
        return introMapper.insertTouristIntro(item);
    }

    public int updateTouristIntro(Map<String, Object> item) {
        return introMapper.updateTouristIntro(item);
    }

    public int insertCulturalIntro(Map<String, Object> item) {
        return introMapper.insertCulturalIntro(item);
    }

    public int updateCulturalIntro(Map<String, Object> item) {
        return introMapper.updateCulturalIntro(item);
    }

    public int insertFestivalIntro(Map<String, Object> item) {
        return introMapper.insertFestivalIntro(item);
    }

    public int updateFestivalIntro(Map<String, Object> item) {
        return introMapper.updateFestivalIntro(item);
    }

    public int insertCourseIntro(Map<String, Object> item) {
        return introMapper.insertCourseIntro(item);
    }

    public int updateCourseIntro(Map<String, Object> item) {
        return introMapper.updateCourseIntro(item);
    }

    public int insertLeportsIntro(Map<String, Object> item) {
        return introMapper.insertLeportsIntro(item);
    }

    public int updateLeportsIntro(Map<String, Object> item) {
        return introMapper.updateLeportsIntro(item);
    }

    public int insertAccommodationIntro(Map<String, Object> item) {
        return introMapper.insertAccommodationIntro(item);
    }

    public int updateAccommodationIntro(Map<String, Object> item) {
        return introMapper.updateAccommodationIntro(item);
    }

    public int insertShoppingIntro(Map<String, Object> item) {
        return introMapper.insertShoppingIntro(item);
    }

    public int updateShoppingIntro(Map<String, Object> item) {
        return introMapper.updateShoppingIntro(item);
    }

    public int insertEateryIntro(Map<String, Object> item) {
        return introMapper.insertEateryIntro(item);
    }

    public int updateEateryIntro(Map<String, Object> item) {
        return introMapper.updateEateryIntro(item);
    }
}
