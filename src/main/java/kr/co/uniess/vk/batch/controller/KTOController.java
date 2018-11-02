package kr.co.uniess.vk.batch.controller;

import kr.co.uniess.vk.batch.component.model.Image;
import kr.co.uniess.vk.batch.component.model.Master;
import kr.co.uniess.vk.batch.repository.model.*;
import kr.co.uniess.vk.batch.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
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
    private DepartmentContentService departmentContentService;

    @Autowired
    private TagsService tagsService;


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

    @SuppressWarnings("unchecked")
    public void insert(Master master, HashMap<String, Object> data) {
        logger.info(":::INSERT:::" + master);

        ContentMasterVO content = ContentMasterVO.valueOf((Map<String, Object>) data.get("master"));
        int count = contentMasterService.insert(content);
        if (count != 1) {
            // TODO throw an error
            return;
        }

        final String cotId = content.getCotId();
        final int contentType = content.getContentType();

        // database master
        DatabaseMasterVO dataBaseMasterVo = DatabaseMasterVO.valueOf((Map<String, Object>) data.get("master")); // TODO data

        // image
        ImageVO firstImageVo = null, firstImage2Vo = null;
        List<Image> imageList = (List<Image>) data.get("image");
        for (Image image : imageList) {
            ImageVO imageVo = ImageVO.valueOf(cotId, image);
            if (imageVo.getUrl().equals(dataBaseMasterVo.getFirstImage())) {
                firstImageVo = imageVo;
            }
            if (imageVo.getUrl().equals(dataBaseMasterVo.getFirstImage2())) {
                firstImage2Vo = imageVo;
            }
            imageService.insert(imageVo);
        }

        if (firstImageVo == null && imageList != null && imageList.size() > 0) {
            firstImageVo = ImageVO.valueOf(cotId, imageList.get(0));
        }

        if (firstImage2Vo == null && imageList != null && imageList.size() > 0) {
            firstImage2Vo = ImageVO.valueOf(cotId, imageList.get(0));
        }

        dataBaseMasterVo.setFirstImage(firstImageVo == null ? null : firstImageVo.getImageId());
        dataBaseMasterVo.setFirstImage2(firstImage2Vo == null ? null : firstImage2Vo.getImageId());
        databaseMasterService.insert(dataBaseMasterVo);

        // intro
        Map<String, Object> introMap = (Map<String, Object>) data.get("intro");
        switch (contentType) {
            case TYPE_TOURIST:
                introService.insertTouristIntro(TouristIntroVO.valueOf(cotId, introMap));
                break;
            case TYPE_CULTURAL:
                introService.insertCulturalIntro(CulturalIntroVO.valueOf(cotId, introMap));
                break;
            case TYPE_FESTIVAL:
                introService.insertFestivalIntro(FestivalIntroVO.valueOf(cotId, introMap));
                break;
            case TYPE_COURSE:
                introService.insertCourseIntro(CourseIntroVO.valueOf(cotId, introMap));
                break;
            case TYPE_LEPORTS:
                introService.insertLeportsIntro(LeportsIntroVO.valueOf(cotId, introMap));
                break;
            case TYPE_ACCOMMODATION:
                introService.insertAccommodationIntro(AccommodationIntroVO.valueOf(cotId, introMap));
                break;
            case TYPE_SHOPPING:
                introService.insertShoppingIntro(ShoppingIntroVO.valueOf(cotId, introMap));
                break;
            case TYPE_EATERY:
                introService.insertEateryIntro(EateryIntroVO.valueOf(cotId, introMap));
                break;
        }

        // info
        List<Map<String, Object>> infoList = (List<Map<String, Object>>) data.get("info");
        switch (content.getContentType()) {
            case TYPE_COURSE: {
                List<CourseInfoVO> list = new ArrayList<>(infoList.size());
                for (Map<String, Object> item : infoList) {
                    list.add(CourseInfoVO.valueOf(cotId, item));
                }
                infoService.insertCourseInfoList(list);
                break;
            }
            case TYPE_ACCOMMODATION: {
                List<AccommodationInfoVO> list = new ArrayList<>(infoList.size());
                for (Map<String, Object> item : infoList) {
                    list.add(AccommodationInfoVO.valueOf(cotId, item));
                }
                infoService.insertAccommodationInfoList(list);
                break;
            }
            default: {
                List<DetailInfoVO> list = new ArrayList<>(infoList.size());
                for (Map<String, Object> item : infoList) {
                    list.add(DetailInfoVO.valueOf(cotId, item));
                }
                infoService.insertDetailInfoList(list);
            }
        }

        //  department 무장애관광
        if (master.isWithTour()) {
            infoService.deleteDetailInfoWithTour(cotId);
            infoService.insertDetailInfo(DetailWithTourVO.valueOf(cotId, (Map<String, Object>) data.get("withtour")));

            String otdId = DepartmentContentVO.OTD_ID_WITHTOUR;
            departmentContentService.insert(DepartmentContentVO.valueOf(otdId, cotId));
        }

        // department 생태관광
        if (master.isGreenTour()) {
            String otdId = DepartmentContentVO.OTD_ID_GREENTOUR;
            departmentContentService.insert(DepartmentContentVO.valueOf(otdId, cotId));
        }

        // department 한국관광품질인증
        if (master.getTitle().contains("한국관광품질인증")) {
            String otdId = DepartmentContentVO.OTD_ID_INDUSTRYTOUR;
            departmentContentService.insert(DepartmentContentVO.valueOf(otdId, cotId));
        }

        // TODO department 산업관광


        // TODO tag
        if (master.getCat1().equals("A01") || master.getCat1().equals("A02")) {

        } else if (master.getCat1().equals("A03")) {

        } else if (master.getCat1().equals("A04")) {

        } else if (master.getCat1().equals("A05")) {

        } else if (master.getCat1().equals("B02")) {

        } else if (master.getCat1().equals("C01")) {

        }
    }

    @SuppressWarnings("unchecked")
    public void update(Master master, HashMap<String, Object> data) {
        logger.info(":::UPDATE:::" + master);

        ContentMasterVO content = ContentMasterVO.valueOf((Map<String, Object>) data.get("master"));
        contentMasterService.update(content);

        final String cotId = content.getCotId();
        final int contentType = content.getContentType();

        // database master
        DatabaseMasterVO dataBaseMasterVo = DatabaseMasterVO.valueOf((Map<String, Object>) data.get("master")); // TODO data

        // image
        ImageVO firstImageVo = null, firstImage2Vo = null;
        List<Image> imageList = (List<Image>) data.get("image");
        for (Image image : imageList) {
            ImageVO imageVo = ImageVO.valueOf(cotId, image);
            if (imageVo.getUrl().equals(dataBaseMasterVo.getFirstImage())) {
                firstImageVo = imageVo;
            }
            if (imageVo.getUrl().equals(dataBaseMasterVo.getFirstImage2())) {
                firstImage2Vo = imageVo;
            }
            if (imageService.findOneByCotId(cotId, image.getOriginimgurl()) == null) {
                imageService.insert(imageVo);
            } else {
                imageService.update(ImageVO.valueOf(cotId, image));
            }
        }

        if (firstImageVo == null && imageList != null && imageList.size() > 0) {
            firstImageVo = ImageVO.valueOf(cotId, imageList.get(0));
        }

        if (firstImage2Vo == null && imageList != null && imageList.size() > 0) {
            firstImage2Vo = ImageVO.valueOf(cotId, imageList.get(0));
        }

        dataBaseMasterVo.setFirstImage(firstImageVo == null ? null : firstImageVo.getImageId());
        dataBaseMasterVo.setFirstImage2(firstImage2Vo == null ? null : firstImage2Vo.getImageId());

        // database master
        if (databaseMasterService.findOne(cotId) == null) {
            databaseMasterService.insert(dataBaseMasterVo);
        } else {
            databaseMasterService.update(dataBaseMasterVo);
        }

        // intro
        Map<String, Object> introMap = (Map<String, Object>) data.get("intro");
        switch (contentType) {
            case TYPE_TOURIST:
                introService.updateTouristIntro(TouristIntroVO.valueOf(cotId, introMap));
                break;
            case TYPE_CULTURAL:
                introService.updateCulturalIntro(CulturalIntroVO.valueOf(cotId, introMap));
                break;
            case TYPE_FESTIVAL:
                introService.updateFestivalIntro(FestivalIntroVO.valueOf(cotId, introMap));
                break;
            case TYPE_COURSE:
                introService.updateCourseIntro(CourseIntroVO.valueOf(cotId, introMap));
                break;
            case TYPE_LEPORTS:
                introService.updateLeportsIntro(LeportsIntroVO.valueOf(cotId, introMap));
                break;
            case TYPE_ACCOMMODATION:
                introService.updateAccommodationIntro(AccommodationIntroVO.valueOf(cotId, introMap));
                break;
            case TYPE_SHOPPING:
                introService.updateShoppingIntro(ShoppingIntroVO.valueOf(cotId, introMap));
                break;
            case TYPE_EATERY:
                introService.updateEateryIntro(EateryIntroVO.valueOf(cotId, introMap));
                break;
        }

        // info
        List<Map<String, Object>> infoList = (List<Map<String, Object>>) data.get("info");
        switch (contentType) {
            case TYPE_COURSE:
                for (Map<String, Object> item : infoList) {
                    infoService.updateCourseInfo(CourseInfoVO.valueOf(cotId, item));
                }
                break;
            case TYPE_ACCOMMODATION:
                for (Map<String, Object> item : infoList) {
                    infoService.updateAccommodationInfo(AccommodationInfoVO.valueOf(cotId, item));
                }
                break;
            default:
                for (Map<String, Object> item : infoList) {
                    infoService.updateDetailInfo(DetailInfoVO.valueOf(cotId, item));
                }
        }

        // department 무장애관광
        if (master.isWithTour()) {
            infoService.deleteDetailInfoWithTour(cotId);
            infoService.insertDetailInfo(DetailWithTourVO.valueOf(cotId, (Map<String, Object>) data.get("withtour")));

            String otdId = DepartmentContentVO.OTD_ID_WITHTOUR;
            departmentContentService.insert(DepartmentContentVO.valueOf(otdId, cotId));
        }

        // TODO department 생태관광
        if (master.isGreenTour()) {

        }

        // TODO department 한국관광품질인증

        // TODO department 산업관광

        // TODO tag
        if (master.getCat1().equals("A01") || master.getCat1().equals("A02")) {

        } else if (master.getCat1().equals("A03")) {

        } else if (master.getCat1().equals("A04")) {

        } else if (master.getCat1().equals("A05")) {

        } else if (master.getCat1().equals("B02")) {

        } else if (master.getCat1().equals("C01")) {

        }
    }

    private static int getIntValue(Map<String, Object> map, String name) {
        String string = map.get(name).toString();
        return Integer.parseInt(string);
    }

    private static long getLongValue(Map<String, Object> map, String name) {
        String string = map.get(name).toString();
        return Long.parseLong(string);
    }

    private static float getFloatValue(Map<String, Object> map, String name) {
        String string = map.get(name).toString();
        return Float.parseFloat(string);
    }
}
