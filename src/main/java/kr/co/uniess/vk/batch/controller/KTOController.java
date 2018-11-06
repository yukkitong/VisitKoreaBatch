package kr.co.uniess.vk.batch.controller;

import kr.co.uniess.vk.batch.component.model.ApiData;
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


    public void process(List<Map<String, Object>> list) {
        if (list == null || list.size() == 0) return;
        // Update database base on the results
        for (Map<String, Object> item : list) {
            final String contentId = ((Master) item.get("master")).getContentId();
            if (contentMasterService.findOne(contentId) == null) {
                insert(item);
            } else {
                update(item);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void insert(Map<String, Object> item) {

        logger.info(":::INSERT:::");

        Master master = (Master) item.get("master");
        ContentMasterVO content = ContentMasterVO.valueOf(master);
        int count = contentMasterService.insert(content);
        if (count != 1) {
            // TODO throw an error
            return;
        }

        final String cotId = content.getCotId();
        final int contentType = content.getContentType();

        // database master
        DatabaseMasterVO dataBaseMasterVo = DatabaseMasterVO.valueOf((ApiData) item.get("common")); // TODO data

        // image
        ImageVO firstImageVo = null, firstImage2Vo = null;
        List<ApiData> imageList = (List<ApiData>) item.get("image");
        if (imageList != null) {
            for (ApiData image : imageList) {
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
        }

        dataBaseMasterVo.setFirstImage(firstImageVo == null ? null : firstImageVo.getImageId());
        dataBaseMasterVo.setFirstImage2(firstImage2Vo == null ? null : firstImage2Vo.getImageId());
        databaseMasterService.insert(dataBaseMasterVo);

        // intro
        ApiData introMap = (ApiData) item.get("intro");
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
        List<ApiData> infoList = (List<ApiData>) item.get("info");
        switch (content.getContentType()) {
            case TYPE_COURSE: {
                List<CourseInfoVO> list = new ArrayList<>(infoList.size());
                for (ApiData i : infoList) {
                    list.add(CourseInfoVO.valueOf(cotId, i));
                }
                infoService.insertCourseInfoList(list);
                break;
            }
            case TYPE_ACCOMMODATION: {
                List<AccommodationInfoVO> list = new ArrayList<>(infoList.size());
                for (ApiData i : infoList) {
                    list.add(AccommodationInfoVO.valueOf(cotId, i));
                }
                infoService.insertAccommodationInfoList(list);
                break;
            }
            default: {
                List<DetailInfoVO> list = new ArrayList<>(infoList.size());
                for (ApiData i : infoList) {
                    list.add(DetailInfoVO.valueOf(cotId, i));
                }
                infoService.insertDetailInfoList(list);
            }
        }

        //  department 무장애관광
        if (master.isWithTour()) {
            infoService.deleteDetailInfoWithTour(cotId);
            infoService.insertDetailInfo(DetailWithTourVO.valueOf(cotId, (ApiData) item.get("withtour")));

            String otdId = DepartmentContentVO.OTD_ID_WITHTOUR;
            departmentContentService.insert(DepartmentContentVO.valueOf(otdId, cotId));
        }

        // department 생태관광
        if (master.isGreenTour()) {
            String otdId = DepartmentContentVO.OTD_ID_GREENTOUR;
            departmentContentService.insert(DepartmentContentVO.valueOf(otdId, cotId));
        }

        // department 한국관광품질인증
        if (content.getTitle().contains("한국관광품질인증")) {
            String otdId = DepartmentContentVO.OTD_ID_INDUSTRYTOUR;
            departmentContentService.insert(DepartmentContentVO.valueOf(otdId, cotId));
        }

        // TODO department 산업관광


        // TODO tag
        if (dataBaseMasterVo.getCat1().equals("A01") || dataBaseMasterVo.getCat1().equals("A02")) {

        } else if (dataBaseMasterVo.getCat1().equals("A03")) {

        } else if (dataBaseMasterVo.getCat1().equals("A04")) {

        } else if (dataBaseMasterVo.getCat1().equals("A05")) {

        } else if (dataBaseMasterVo.getCat1().equals("B02")) {

        } else if (dataBaseMasterVo.getCat1().equals("C01")) {

        }
    }

    @SuppressWarnings("unchecked")
    public void update(Map<String, Object> item) {
        logger.info(":::UPDATE:::");

        Master master = (Master) item.get("master");
        ContentMasterVO content = ContentMasterVO.valueOf(master);
        contentMasterService.update(content);

        final String cotId = content.getCotId();
        final int contentType = content.getContentType();

        // database master
        DatabaseMasterVO dataBaseMasterVo = DatabaseMasterVO.valueOf((ApiData) item.get("common")); // TODO data

        // image
        ImageVO firstImageVo = null, firstImage2Vo = null;
        List<ApiData> imageList = (List<ApiData>) item.get("image");
        if (imageList != null) {
            for (ApiData image : imageList) {
                ImageVO imageVo = ImageVO.valueOf(cotId, image);
                if (imageVo.getUrl().equals(dataBaseMasterVo.getFirstImage())) {
                    firstImageVo = imageVo;
                }
                if (imageVo.getUrl().equals(dataBaseMasterVo.getFirstImage2())) {
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

        dataBaseMasterVo.setFirstImage(firstImageVo == null ? null : firstImageVo.getImageId());
        dataBaseMasterVo.setFirstImage2(firstImage2Vo == null ? null : firstImage2Vo.getImageId());

        // database master
        if (databaseMasterService.findOne(cotId) == null) {
            databaseMasterService.insert(dataBaseMasterVo);
        } else {
            databaseMasterService.update(dataBaseMasterVo);
        }

        // intro
        ApiData introMap = (ApiData) item.get("intro");
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
        List<ApiData> infoList = (List<ApiData>) item.get("info");
        switch (contentType) {
            case TYPE_COURSE:
                for (ApiData i : infoList) {
                    infoService.updateCourseInfo(CourseInfoVO.valueOf(cotId, i));
                }
                break;
            case TYPE_ACCOMMODATION:
                for (ApiData i : infoList) {
                    infoService.updateAccommodationInfo(AccommodationInfoVO.valueOf(cotId, i));
                }
                break;
            default:
                for (ApiData i : infoList) {
                    infoService.updateDetailInfo(DetailInfoVO.valueOf(cotId, i));
                }
        }

        // department 무장애관광
        if (master.isWithTour()) {
            infoService.deleteDetailInfoWithTour(cotId);
            infoService.insertDetailInfo(DetailWithTourVO.valueOf(cotId, (ApiData) item.get("withtour")));

            String otdId = DepartmentContentVO.OTD_ID_WITHTOUR;
            departmentContentService.insert(DepartmentContentVO.valueOf(otdId, cotId));
        }

        // TODO department 생태관광
        if (master.isGreenTour()) {

        }

        // TODO department 한국관광품질인증

        // TODO department 산업관광

        // TODO tag
        if (dataBaseMasterVo.getCat1().equals("A01") || dataBaseMasterVo.getCat1().equals("A02")) {

        } else if (dataBaseMasterVo.getCat1().equals("A03")) {

        } else if (dataBaseMasterVo.getCat1().equals("A04")) {

        } else if (dataBaseMasterVo.getCat1().equals("A05")) {

        } else if (dataBaseMasterVo.getCat1().equals("B02")) {

        } else if (dataBaseMasterVo.getCat1().equals("C01")) {

        }
    }

    private static int getIntValue(ApiData map, String name) {
        String string = map.get(name).toString();
        return Integer.parseInt(string);
    }

    private static long getLongValue(ApiData map, String name) {
        String string = map.get(name).toString();
        return Long.parseLong(string);
    }

    private static float getFloatValue(ApiData map, String name) {
        String string = map.get(name).toString();
        return Float.parseFloat(string);
    }
}
