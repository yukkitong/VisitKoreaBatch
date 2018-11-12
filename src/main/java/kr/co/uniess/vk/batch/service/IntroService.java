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
        ensureCotId(item.getCotid());
        return touristIntroMapper.insert(item);
    }

    public int updateTouristIntro(TouristIntroVO item) {
        ensureCotId(item.getCotid());
        return touristIntroMapper.update(item);
    }

    public int deleteTouristIntro(String cotId) {
        return touristIntroMapper.delete(cotId);
    }

    public int insertCulturalIntro(CulturalIntroVO item) {
        ensureCotId(item.getCotid());
        return culturalIntroMapper.insert(item);
    }

    public int updateCulturalIntro(CulturalIntroVO item) {
        ensureCotId(item.getCotid());
        return culturalIntroMapper.update(item);
    }

    public int deleteCulturalIntro(String cotId) {
        return culturalIntroMapper.delete(cotId);
    }

    public int insertFestivalIntro(FestivalIntroVO item) {
        ensureCotId(item.getCotid());
        return festivalIntroMapper.insert(item);
    }

    public int updateFestivalIntro(FestivalIntroVO item) {
        ensureCotId(item.getCotid());
        return festivalIntroMapper.update(item);
    }

    public int deleteFestivalIntro(String cotId) {
        return festivalIntroMapper.delete(cotId);
    }

    public int insertCourseIntro(CourseIntroVO item) {
        ensureCotId(item.getCotid());
        return courseIntroMapper.insert(item);
    }

    public int updateCourseIntro(CourseIntroVO item) {
        ensureCotId(item.getCotid());
        return courseIntroMapper.update(item);
    }

    public int deleteCourseIntro(String cotId) {
        return courseIntroMapper.delete(cotId);
    }

    public int insertLeportsIntro(LeportsIntroVO item) {
        ensureCotId(item.getCotid());
        return leportsIntroMapper.insert(item);
    }

    public int updateLeportsIntro(LeportsIntroVO item) {
        ensureCotId(item.getCotid());
        return leportsIntroMapper.update(item);
    }

    public int deleteLeportsIntro(String cotId) {
        return leportsIntroMapper.delete(cotId);
    }

    public int insertAccommodationIntro(AccommodationIntroVO item) {
        ensureCotId(item.getCotid());
        return accommodationIntroMapper.insert(item);
    }

    public int updateAccommodationIntro(AccommodationIntroVO item) {
        ensureCotId(item.getCotid());
        return accommodationIntroMapper.update(item);
    }

    public int deleteAccommodationIntro(String cotId) {
        return accommodationIntroMapper.delete(cotId);
    }

    public int insertShoppingIntro(ShoppingIntroVO item) {
        ensureCotId(item.getCotid());
        return shoppingIntroMapper.insert(item);
    }

    public int updateShoppingIntro(ShoppingIntroVO item) {
        ensureCotId(item.getCotid());
        return shoppingIntroMapper.update(item);
    }

    public int deleteShoppingIntro(String cotId) {
        return shoppingIntroMapper.delete(cotId);
    }

    public int insertEateryIntro(EateryIntroVO item) {
        ensureCotId(item.getCotid());
        return eateryIntroMapper.insert(item);
    }

    public int updateEateryIntro(EateryIntroVO item) {
        ensureCotId(item.getCotid());
        return eateryIntroMapper.update(item);
    }

    public int deleteEateryIntro(String cotId) {
        return eateryIntroMapper.delete(cotId);
    }

    private void ensureCotId(String cotId) {
        if (cotId == null) {
            throw new IllegalArgumentException("`COT_ID` must not be null.");
        }
    }
}
