package kr.co.uniess.vk.batch.controller;

import kr.co.uniess.vk.batch.component.model.Master;
import kr.co.uniess.vk.batch.repository.model.*;
import kr.co.uniess.vk.batch.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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


    private String createCotId() {
        return UUID.randomUUID().toString();
    }

    public void process(List<Map<String, Object>> list) {
        if (list == null || list.size() == 0) return;
        // Update database base on the results
        for (Map<String, Object> item : list) {
            final String contentId = ((Map<String, Object>) item.get("master")).get("contentid").toString();
            final String cotId = contentMasterService.findOne(contentId);
            if (cotId == null) {
                insert(createCotId(), item);
            } else {
                update(cotId, item);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void insert(String cotId, Map<String, Object> item) {
        Master master = Master.wrap((Map<String, Object>) item.get("master"));
        Map<String, Object> common = (Map<String, Object>) item.get("common");

        logger.info(":::INSERT:::{}", master);

        ContentMasterVO content = ContentMasterVO.valueOf(cotId, master, common);
        int count = contentMasterService.insert(content);
        if (count != 1) {
            // TODO throw an error
            return;
        }

        // database master
        DatabaseMasterVO dataBaseMasterVo = DatabaseMasterVO.valueOf(cotId, common); // TODO data

        // image
        ImageVO firstImageVo = null, firstImage2Vo = null;
        List<Map<String, Object>> imageList = (List<Map<String, Object>>) item.get("image");
        if (imageList != null) {
            for (Map<String, Object> image : imageList) {
                ImageVO imageVo = ImageVO.valueOf(cotId, image);
                if (common.get("firstimage") != null && common.get("firstimage").toString().equals(imageVo.getUrl())) {
                    firstImageVo = imageVo;
                }
                if (common.get("firstimage2") != null && common.get("firstimage2").toString().equals(imageVo.getUrl())) {
                    firstImage2Vo = imageVo;
                }
                imageService.insert(imageVo);
            }

            if (firstImageVo == null && imageList.size() > 0) {
                firstImageVo = ImageVO.valueOf(cotId, imageList.get(0));
            }

            if (firstImage2Vo == null && imageList.size() > 0) {
                firstImage2Vo = ImageVO.valueOf(cotId, imageList.get(0));
            }
        }

        dataBaseMasterVo.setFirstimage(firstImageVo == null ? null : firstImageVo.getImageid());
        dataBaseMasterVo.setFirstimage2(firstImage2Vo == null ? null : firstImage2Vo.getImageid());
        databaseMasterService.insert(dataBaseMasterVo);

        final int contentTypeId = master.getContentTypeId();
        // intro
        Map<String, Object> introMap = (Map<String, Object>) item.get("intro");
        switch (contentTypeId) {
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

        // info TODO: -> update에도 아래 코드 이용
        List<Map<String, Object>> infoList = (List<Map<String, Object>>) item.get("info");
        if (infoList != null) {
            switch (contentTypeId) {
                case TYPE_COURSE: {
                    List<CourseInfoVO> list = new ArrayList<>(infoList.size());
                    for (Map<String, Object> i : infoList) {
                        list.add(CourseInfoVO.valueOf(cotId, i));
                    }
                    infoService.insertCourseInfoList(list);
                    break;
                }
                case TYPE_ACCOMMODATION: {
                    List<AccommodationInfoVO> list = new ArrayList<>(infoList.size());
                    for (Map<String, Object> i : infoList) {
                        list.add(AccommodationInfoVO.valueOf(cotId, i));
                    }
                    infoService.insertAccommodationInfoList(list);
                    break;
                }
                default: {
                    List<DetailInfoVO> list = new ArrayList<>(infoList.size());
                    for (Map<String, Object> i : infoList) {
                        list.add(DetailInfoVO.valueOf(cotId, i));
                    }
                    infoService.insertDetailInfoList(list);
                }
            }
        }

        //  department 무장애관광
        if (master.isWithTour()) {
            infoService.deleteDetailInfoWithTour(cotId);
            infoService.insertDetailInfo(DetailWithTourVO.valueOf(cotId, (Map<String, Object>) item.get("withtour")));

            // TODO 없으면 INSERT
            String otdId = DepartmentContentVO.OTD_ID_WITHTOUR;
            departmentContentService.insert(DepartmentContentVO.valueOf(otdId, cotId));
        }

        // department 생태관광
        if (master.isGreenTour()) {
            // TODO 없으면 INSERT
            String otdId = DepartmentContentVO.OTD_ID_GREENTOUR;
            departmentContentService.insert(DepartmentContentVO.valueOf(otdId, cotId));
        }

        // department 한국관광품질인증
        if (content.getTitle().contains("한국관광품질인증")) {
            // TODO 없으면 INSERT
            String otdId = DepartmentContentVO.OTD_ID_INDUSTRYTOUR;
            departmentContentService.insert(DepartmentContentVO.valueOf(otdId, cotId));
        }

        // TODO department 산업관광


        // TODO tag
        // TODO 없으면 INSERT
        if (dataBaseMasterVo.getCat1().equals("A01") || dataBaseMasterVo.getCat1().equals("A02")) {

        } else if (dataBaseMasterVo.getCat1().equals("A03")) {

        } else if (dataBaseMasterVo.getCat1().equals("A04")) {

        } else if (dataBaseMasterVo.getCat1().equals("A05")) {

        } else if (dataBaseMasterVo.getCat1().equals("B02")) {

        } else if (dataBaseMasterVo.getCat1().equals("C01")) {

        }
    }

    @SuppressWarnings("unchecked")
    private void update(String cotId, Map<String, Object> item) {
        Master master = Master.wrap((Map<String, Object>) item.get("master"));
        Map<String, Object> common = (Map<String, Object>) item.get("common");

        logger.info(":::UPDATE:::{}", master);

        ContentMasterVO content = ContentMasterVO.valueOf(cotId, master, common);
        contentMasterService.update(content);

        // database master
        DatabaseMasterVO dataBaseMasterVo = DatabaseMasterVO.valueOf(cotId, common);

        // image
        ImageVO firstImageVo = null, firstImage2Vo = null;
        List<Map<String, Object>> imageList = (List<Map<String, Object>>) item.get("image");
        if (imageList != null) {
            for (Map<String, Object> image : imageList) {
                ImageVO imageVo = ImageVO.valueOf(cotId, image);
                if (common.get("firstimage") != null && common.get("firstimage").toString().equals(imageVo.getUrl())) {
                    firstImageVo = imageVo;
                }
                if (common.get("firstimage2") != null && common.get("firstimage2").toString().equals(imageVo.getUrl())) {
                    firstImage2Vo = imageVo;
                }
                if (imageService.findOneByCotId(cotId, imageVo.getUrl()) == null) {
                    imageService.insert(imageVo);
                } else {
                    imageService.update(imageVo);
                }
            }

            if (firstImageVo == null && imageList.size() > 0) {
                firstImageVo = ImageVO.valueOf(cotId, imageList.get(0));
            }

            if (firstImage2Vo == null && imageList.size() > 0) {
                firstImage2Vo = ImageVO.valueOf(cotId, imageList.get(0));
            }
        }

        dataBaseMasterVo.setFirstimage(firstImageVo == null ? null : firstImageVo.getImageid());
        dataBaseMasterVo.setFirstimage2(firstImage2Vo == null ? null : firstImage2Vo.getImageid());

        // database master
        if (databaseMasterService.findOne(cotId) == null) {
            databaseMasterService.insert(dataBaseMasterVo);
        } else {
            databaseMasterService.update(dataBaseMasterVo);
        }


        final int contentTypeId = master.getContentTypeId();
        // intro
        Map<String, Object> introMap = (Map<String, Object>) item.get("intro");
        switch (contentTypeId) {
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
        List<Map<String, Object>> infoList = (List<Map<String, Object>>) item.get("info");
        if (infoList != null) {
            switch (contentTypeId) {
                case TYPE_COURSE:
                    infoService.deleteCourseInfo(cotId);
                    for (Map<String, Object> i : infoList) {
                        infoService.insertCourseInfo(CourseInfoVO.valueOf(cotId, i));
                    }
                    break;
                case TYPE_ACCOMMODATION:
                    infoService.deleteCourseInfo(cotId);
                    for (Map<String, Object> i : infoList) {
                        infoService.insertAccommodationInfo(AccommodationInfoVO.valueOf(cotId, i));
                    }
                    break;
                default:
                    infoService.deleteDetailInfo(cotId);
                    for (Map<String, Object> i : infoList) {
                        infoService.insertDetailInfo(DetailInfoVO.valueOf(cotId, i));
                    }
            }
        }

        // department 무장애관광
        if (master.isWithTour()) {
            infoService.deleteDetailInfoWithTour(cotId);
            infoService.insertDetailInfo(DetailWithTourVO.valueOf(cotId, (Map<String, Object>) item.get("withtour")));

            // TODO 없으면 INSERT
            String otdId = DepartmentContentVO.OTD_ID_WITHTOUR;
            departmentContentService.insert(DepartmentContentVO.valueOf(otdId, cotId));
        }

        // TODO department 생태관광
        if (master.isGreenTour()) {
            // TODO 없으면 INSERT
        }

        // TODO department 한국관광품질인증

        // TODO department 산업관광

        // TODO tag
        // TODO 없으면 INSERT
        if (dataBaseMasterVo.getCat1().equals("A01") || dataBaseMasterVo.getCat1().equals("A02")) {

        } else if (dataBaseMasterVo.getCat1().equals("A03")) {

        } else if (dataBaseMasterVo.getCat1().equals("A04")) {

        } else if (dataBaseMasterVo.getCat1().equals("A05")) {

        } else if (dataBaseMasterVo.getCat1().equals("B02")) {

        } else if (dataBaseMasterVo.getCat1().equals("C01")) {

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
