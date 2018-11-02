package kr.co.uniess.vk.batch.service;

import kr.co.uniess.vk.batch.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class IntroService {

    @Autowired @Lazy
    private TouristIntroMapper touristIntroMapper;

    @Autowired @Lazy
    private CulturalIntroMapper culturalIntroMapper;

    @Autowired @Lazy
    private FestivalIntroMapper festivalIntroMapper;

    @Autowired @Lazy
    private CourseIntroMapper courseIntroMapper;

    @Autowired @Lazy
    private LeportsIntroMapper leportsIntroMapper;

    @Autowired @Lazy
    private AccommodationIntroMapper accommodationIntroMapper;

    @Autowired @Lazy
    private ShoppingIntroMapper shoppingIntroMapper;

    @Autowired @Lazy
    private EateryIntroMapper eateryIntroMapper;


    public int insertTouristIntro(Map<String, Object> item) {
        return touristIntroMapper.insert(item);
    }

    public int updateTouristIntro(Map<String, Object> item) {
        return touristIntroMapper.update(item);
    }

    public int deleteTouristIntro(String cotId) {
        return touristIntroMapper.delete(cotId);
    }

    public int insertCulturalIntro(Map<String, Object> item) {
        return culturalIntroMapper.insert(item);
    }

    public int updateCulturalIntro(Map<String, Object> item) {
        return culturalIntroMapper.update(item);
    }

    public int deleteCulturalIntro(String cotId) {
        return culturalIntroMapper.delete(cotId);
    }

    public int insertFestivalIntro(Map<String, Object> item) {
        return festivalIntroMapper.insert(item);
    }

    public int updateFestivalIntro(Map<String, Object> item) {
        return festivalIntroMapper.update(item);
    }

    public int deleteFestivalIntro(String cotId) {
        return festivalIntroMapper.delete(cotId);
    }

    public int insertCourseIntro(Map<String, Object> item) {
        return courseIntroMapper.insert(item);
    }

    public int updateCourseIntro(Map<String, Object> item) {
        return courseIntroMapper.update(item);
    }

    public int deleteCourseIntro(String cotId) {
        return courseIntroMapper.delete(cotId);
    }

    public int insertLeportsIntro(Map<String, Object> item) {
        return leportsIntroMapper.insert(item);
    }

    public int updateLeportsIntro(Map<String, Object> item) {
        return leportsIntroMapper.update(item);
    }

    public int deleteLeportsIntro(String cotId) {
        return leportsIntroMapper.delete(cotId);
    }

    public int insertAccommodationIntro(Map<String, Object> item) {
        return accommodationIntroMapper.insert(item);
    }

    public int updateAccommodationIntro(Map<String, Object> item) {
        return accommodationIntroMapper.update(item);
    }

    public int deleteAccommodationIntro(String cotId) {
        return accommodationIntroMapper.delete(cotId);
    }

    public int insertShoppingIntro(Map<String, Object> item) {
        return shoppingIntroMapper.insert(item);
    }

    public int updateShoppingIntro(Map<String, Object> item) {
        return shoppingIntroMapper.update(item);
    }

    public int deleteShoppingIntro(String cotId) {
        return shoppingIntroMapper.delete(cotId);
    }

    public int insertEateryIntro(Map<String, Object> item) {
        return eateryIntroMapper.insert(item);
    }

    public int updateEateryIntro(Map<String, Object> item) {
        return eateryIntroMapper.update(item);
    }

    public int deleteEateryIntro(String cotId) {
        return eateryIntroMapper.delete(cotId);
    }
}
