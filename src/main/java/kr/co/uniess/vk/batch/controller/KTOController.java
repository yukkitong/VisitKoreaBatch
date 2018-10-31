package kr.co.uniess.vk.batch.controller;

import kr.co.uniess.vk.batch.model.DetailWithTour;
import kr.co.uniess.vk.batch.model.Image;
import kr.co.uniess.vk.batch.model.Master;
import kr.co.uniess.vk.batch.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class KTOController {

    private final Logger logger = LoggerFactory.getLogger(KTOController.class);

    /**
     * CONTENT TYPE ID
     */
    private static final int TYPE_TOURIST = 12;
    private static final int TYPE_CULTURAL = 14;
    private static final int TYPE_FESTIVAL = 15;
    private static final int TYPE_COURSE = 25;
    private static final int TYPE_LEPORTS = 28;
    private static final int TYPE_ACCOMMODATION = 32;
    private static final int TYPE_SHOPPING = 38;
    private static final int TYPE_EATERY = 39;


    /**
     * 관광지 TAG_ID ( CAT1 = A01, A02 )
     */
    private final static String TAG_ID_TOURIST = "3f36ca4b-6f45-45cb-9042-265c96a4868c";

    /**
     * 레포츠 TAG_ID ( CAT1 = A03 )
     */
    private final static String TAG_ID_LEPORTS = "e6875575-2cc2-43ba-9651-28d31a7b3e23";

    /**
     * 쇼핑 TAG_ID ( CAT1 = A04 )
     */
    private final static String TAG_ID_SHOPPING = "0f29b431-75ac-4ab4-a892-b247d516b31";

    /**
     * 음식 TAG_ID ( CAT1 = A05 )
     */
    private final static String TAG_ID_EATERY = "11751b64-5bf9-44fa-90cd-e0e1b092caf6";

    /**
     * 숙박 TAG_ID ( CAT1 = B02 )
     */
    private final static String TAG_ID_ACCOMMODATION = "b7023aff-8138-4a00-ae7f-e4fe7b13a61b";

    /**
     * 코스 TAG_ID ( CAT1 = C01 )
     */
    private final static String TAG_ID_COURSE = "8ddf2f14-a1c7-11e8-8165-020027310001";


    @Autowired
    private ContentMasterService contentMasterService;

    @Autowired
    private DatabaseMasterService databaseMasterService;

    @Autowired
    private IntroService introService;

    @Autowired
    private InfoService infoService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private TagsService tagsService;

    @Autowired
    private DetailWithTourService detailWithTourService;


    public void process(HashMap<Master, HashMap<String, Object>> data) {
        if (data == null) return;
        // Update database base on the results
        for (Master master : data.keySet()) {
            final String contentId = master.getContentId();
            if (contentMasterService.findOne(contentId) == null) {
                insert(master, data.get(master));
            } else {
                update(master, data.get(master));
            }
        }
    }

    public void insert(Master master, HashMap<String, Object> data) {
        logger.info("::INSERT:::" + master);

        final String contentId = master.getContentId();
        final int contentTypeId = master.getContentTypeId();

        // Insert intro
        Map<String, Object> intro = (Map<String, Object>) data.get("intro");
        switch (contentTypeId) {
            case TYPE_TOURIST:
                introService.insertTouristIntro(intro);
                break;
            case TYPE_CULTURAL:
                introService.insertCulturalIntro(intro);
                break;
            case TYPE_FESTIVAL:
                introService.insertFestivalIntro(intro);
                break;
            case TYPE_COURSE:
                // TODO citytour? cat1 = C02, cat2 = C0201 일때
                introService.insertCourseIntro(intro);

                break;
            case TYPE_LEPORTS:
                introService.insertLeportsIntro(intro);
                break;
            case TYPE_ACCOMMODATION:
                introService.insertAccommodationIntro(intro);
                break;
            case TYPE_SHOPPING:
                introService.insertShoppingIntro(intro);
                break;
            case TYPE_EATERY:
                introService.insertEateryIntro(intro);
                break;
        }

        // Insert info
        List<Map<String, Object>> infoList = (List<Map<String, Object>>) data.get("info");
        switch (contentTypeId) {
            case TYPE_COURSE:
                infoService.insertCourseInfoList(infoList);
                break;
            case TYPE_ACCOMMODATION:
                infoService.insertAccommodationInfoList(infoList);
                break;
            default:
                infoService.insertDetailInfoList(infoList);
                break;
        }

        // Insert image
        List<Image> images = (List<Image>) data.get("image");
        for (Image image : images) {
            if (imageService.findOneByContentId(contentId, image.getOriginimgurl()) == null) {
                imageService.insert(image);
            } else {
                imageService.update(image);
            }
        }

        if (master.isWithTour()) {
            DetailWithTour withTour = (DetailWithTour) data.get("withtour");
            detailWithTourService.deleteByContentId(contentId);
            // TODO manipulate data weired!!!
            detailWithTourService.insert(withTour);

            // TODO department
            // departmentService.
        }

        if (master.isGreenTour()) {
            // TODO department
        }

        if (master.getTitle().contains("한국관광품질인증")) {

        }

        // TODO department 산업관광


        // TODO Update tag
        if (master.getCat1().equals("A01") || master.getCat1().equals("A02")) {

        } else if (master.getCat1().equals("A03")) {

        } else if (master.getCat1().equals("A04")) {

        } else if (master.getCat1().equals("A05")) {

        } else if (master.getCat1().equals("B02")) {

        } else if (master.getCat1().equals("C01")) {

        }
    }

    public void update(Master master, HashMap<String, Object> data) {
        logger.info("::UPDATE:::" + master);

        final String contentId = master.getContentId();
        final int contentTypeId = master.getContentTypeId();

        // Insert intro
        Map<String, Object> intro = (Map<String, Object>) data.get("intro");
        switch (contentTypeId) {
            case TYPE_TOURIST:
                introService.updateTouristIntro(intro);
                break;
            case TYPE_CULTURAL:
                introService.updateCulturalIntro(intro);
                break;
            case TYPE_FESTIVAL:
                introService.updateFestivalIntro(intro);
                break;
            case TYPE_COURSE:
                // TODO citytour? cat1 = C02, cat2 = C0201 일때
                introService.updateCourseIntro(intro);
                break;
            case TYPE_LEPORTS:
                introService.updateLeportsIntro(intro);
                break;
            case TYPE_ACCOMMODATION:
                introService.updateAccommodationIntro(intro);
                break;
            case TYPE_SHOPPING:
                introService.updateShoppingIntro(intro);
                break;
            case TYPE_EATERY:
                introService.updateEateryIntro(intro);
                break;
        }

        // Insert info
        List<Map<String, Object>> infoList = (List<Map<String, Object>>) data.get("info");
        switch (contentTypeId) {
            case TYPE_COURSE:
                for (Map<String, Object> item : infoList) {
                    infoService.updateCourseInfoList(item);
                }
                break;
            case TYPE_ACCOMMODATION:
                for (Map<String, Object> item : infoList) {
                    infoService.updateAccommodationInfo(item);
                }
                break;
            default:
                for (Map<String, Object> item : infoList) {
                    infoService.updateDetailInfo(item);
                }
                break;
        }

        // Update image
        List<Image> images = (List<Image>) data.get("image");
        for (Image image : images) {
            if (imageService.findOneByContentId(contentId, image.getOriginimgurl()) == null) {
                imageService.insert(image);
            } else {
                imageService.update(image);
            }
        }

        // TODO Update department
        if (master.isWithTour()) {

        }

        if (master.isGreenTour()) {

        }


        // TODO department 산업관광


        // TODO Update tag
        if (master.getCat1().equals("A01") || master.getCat1().equals("A02")) {

        } else if (master.getCat1().equals("A03")) {

        } else if (master.getCat1().equals("A04")) {

        } else if (master.getCat1().equals("A05")) {

        } else if (master.getCat1().equals("B02")) {

        } else if (master.getCat1().equals("C01")) {

        }
    }

    private int getIntValue(Map<String, Object> map, String name) {
        if (map.get(name) == null) return -1;
        String string = map.get(name).toString();
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return -2;
        }
    }

    private long getLongValue(Map<String, Object> map, String name) {
        if (map.get(name) == null) return -1;
        String string = map.get(name).toString();
        try {
            return Long.parseLong(string);
        } catch (NumberFormatException e) {
            return -2;
        }
    }

    private float getFloatValue(Map<String, Object> map, String name) {
        if (map.get(name) == null) return -1;
        String string = map.get(name).toString();
        try {
            return Float.parseFloat(string);
        } catch (NumberFormatException e) {
            return -2;
        }
    }
}
