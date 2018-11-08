package kr.co.uniess.vk.batch.service;

import kr.co.uniess.vk.batch.repository.*;
import kr.co.uniess.vk.batch.repository.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class IntroService {

    @Autowired @Lazy
    private TouristIntroMapper touristIntroMapper;

    @Autowired @Lazy
    private CulturalIntroMapper culturalIntroMapper;

    @Autowired @Lazy
    private FestivalIntroMapper festivalIntroMapper;

    // TODO 여기부터 만들어야함.... VO랑 xml 매퍼 파일 컬럼명 수정
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


    public int insertTouristIntro(TouristIntroVO item) {
        if (item.getCotid() == null) throw new IllegalArgumentException("`COT_ID` must not be null.");
        return touristIntroMapper.insert(item);
    }

    public int updateTouristIntro(TouristIntroVO item) {
        if (item.getCotid() == null) throw new IllegalArgumentException("`COT_ID` must not be null.");
        return touristIntroMapper.update(item);
    }

    public int deleteTouristIntro(String cotId) {
        return touristIntroMapper.delete(cotId);
    }

    public int insertCulturalIntro(CulturalIntroVO item) {
        if (item.getCotid() == null) throw new IllegalArgumentException("`COT_ID` must not be null.");
        return culturalIntroMapper.insert(item);
    }

    public int updateCulturalIntro(CulturalIntroVO item) {
        if (item.getCotid() == null) throw new IllegalArgumentException("`COT_ID` must not be null.");
        return culturalIntroMapper.update(item);
    }

    public int deleteCulturalIntro(String cotId) {
        return culturalIntroMapper.delete(cotId);
    }

    public int insertFestivalIntro(FestivalIntroVO item) {
        if (item.getCotid() == null) throw new IllegalArgumentException("`COT_ID` must not be null.");
        return festivalIntroMapper.insert(item);
    }

    public int updateFestivalIntro(FestivalIntroVO item) {
        if (item.getCotid() == null) throw new IllegalArgumentException("`COT_ID` must not be null.");
        return festivalIntroMapper.update(item);
    }

    public int deleteFestivalIntro(String cotId) {
        return festivalIntroMapper.delete(cotId);
    }

    public int insertCourseIntro(CourseIntroVO item) {
        if (item.getCotid() == null) throw new IllegalArgumentException("`COT_ID` must not be null.");
        return courseIntroMapper.insert(item);
    }

    public int updateCourseIntro(CourseIntroVO item) {
        if (item.getCotid() == null) throw new IllegalArgumentException("`COT_ID` must not be null.");
        return courseIntroMapper.update(item);
    }

    public int deleteCourseIntro(String cotId) {
        return courseIntroMapper.delete(cotId);
    }

    public int insertLeportsIntro(LeportsIntroVO item) {
        if (item.getCotid() == null) throw new IllegalArgumentException("`COT_ID` must not be null.");
        return leportsIntroMapper.insert(item);
    }

    public int updateLeportsIntro(LeportsIntroVO item) {
        if (item.getCotid() == null) throw new IllegalArgumentException("`COT_ID` must not be null.");
        return leportsIntroMapper.update(item);
    }

    public int deleteLeportsIntro(String cotId) {
        return leportsIntroMapper.delete(cotId);
    }

    public int insertAccommodationIntro(AccommodationIntroVO item) {
        if (item.getCotid() == null) throw new IllegalArgumentException("`COT_ID` must not be null.");
        return accommodationIntroMapper.insert(item);
    }

    public int updateAccommodationIntro(AccommodationIntroVO item) {
        if (item.getCotid() == null) throw new IllegalArgumentException("`COT_ID` must not be null.");
        return accommodationIntroMapper.update(item);
    }

    public int deleteAccommodationIntro(String cotId) {
        return accommodationIntroMapper.delete(cotId);
    }

    public int insertShoppingIntro(ShoppingIntroVO item) {
        if (item.getCotid() == null) throw new IllegalArgumentException("`COT_ID` must not be null.");
        return shoppingIntroMapper.insert(item);
    }

    public int updateShoppingIntro(ShoppingIntroVO item) {
        if (item.getCotid() == null) throw new IllegalArgumentException("`COT_ID` must not be null.");
        return shoppingIntroMapper.update(item);
    }

    public int deleteShoppingIntro(String cotId) {
        return shoppingIntroMapper.delete(cotId);
    }

    public int insertEateryIntro(EateryIntroVO item) {
        if (item.getCotid() == null) throw new IllegalArgumentException("`COT_ID` must not be null.");
        return eateryIntroMapper.insert(item);
    }

    public int updateEateryIntro(EateryIntroVO item) {
        if (item.getCotid() == null) throw new IllegalArgumentException("`COT_ID` must not be null.");
        return eateryIntroMapper.update(item);
    }

    public int deleteEateryIntro(String cotId) {
        return eateryIntroMapper.delete(cotId);
    }
}
