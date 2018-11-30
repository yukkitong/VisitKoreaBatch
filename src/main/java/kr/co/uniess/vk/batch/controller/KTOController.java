package kr.co.uniess.vk.batch.controller;

import kr.co.uniess.vk.batch.component.model.GreenMaster;
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

/**
 * NOTE. 이미지 처리에 대한 전체 적인 정리 및 생각
 * - 이미지는 API, 액셀파일 모두 2가지의 방법으로 전달받는다.
 * - 이미지는 API를 통해 100% 전달되지 않는다.
 * - 그렇다면 100%는 어디를 통해 전달되는가? 바로 액셀파일이다.
 * - API를 통해 전달되는 이미지의 종류는 메인 이미지 (마스터정보 `first_image`)와 상세 이미지가 있다.
 * - 액셀파일을 통해 전달되는 이미지는 `MAINIMGCHK` 컬럼에 별도 표시 `O`(메인)로 메인 이미지와 상세를 구분한다.
 *
 * => 어떤 이유(저작권 문제 등)로 이미지의 100%를 API를 통해 전달하지 못하는 문제가 발생하고 있다.
 *    그렇기 때문에 API를 통해 전달받은 이미지를 등록 또는 수정 처리할 경우에는 `임시조치`로 판단하고 처리해야 한다.
 *    액셀파일로 전달된 이미지를 처리할때 비로소 이미지 `보정작업`과 함께 전체적인 이미지가 바로 잡혀가게 된다.
 *
 *    `임시조치` : 메인 이미지 선정 방식 즉, 메인 이미지가 특정되지 않은 경우 상세 이미지 중에서 메인 이미지를 선정할 수 있다.
 *    `보정작업` : 임시조치의 결과를 수정하여 액셀에 명시된 메인이미지로 변경 처리함과 동시에 `싱크작업`을 한다.
 *    `싱크작업` : 데이터베이스에 등록된 이미지와 액셀의 내용을 일치시키는 작업을 말한다.
 */

@Controller
public class KTOController {

    private final Logger logger = LoggerFactory.getLogger(KTOController.class);


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
    private ContentTagsService contentTagsService;


    private String createCotId() {
        return UUID.randomUUID().toString();
    }
    private String createImgId() {
        return UUID.randomUUID().toString();
    }

    @SuppressWarnings("unchecked")
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
    private void insert(String newCotId, Map<String, Object> item) {
        Master master = Master.wrap((Map<String, Object>) item.get("master"));
        logger.info(":::INSERT:::{}", master);
        if (master.isGreenTour()) {
            GreenMaster greenMaster = (GreenMaster) master;

            // green tour - image
            ImageVO firstImageVo = null;
            if (greenMaster.get("firstimage") != null) {
                String url = greenMaster.get("firstimage").toString();

                firstImageVo = new ImageVO();
                firstImageVo.setCotid(newCotId);
                firstImageVo.setUrl(url);
                firstImageVo.setImagedescription(greenMaster.getTitle());
                firstImageVo.setIsthubnail(1);

                String imageId = imageService.findOneByCotId(newCotId, url);
                if (imageId == null) {
                    firstImageVo.setImgid(createImgId());
                    imageService.insert(firstImageVo);
                } else {
                    // 이미지가 존재하므로 스킵
                    firstImageVo.setImgid(imageId);
                }
            }

            // green tour master (content_master)
            ContentMasterVO content = ContentMasterVO.valueOf(newCotId, greenMaster);
            contentMasterService.insert(content);

            // green tour master (database_master)
            DatabaseMasterVO dataBaseMasterVo = DatabaseMasterVO.valueOf(newCotId, greenMaster);
            dataBaseMasterVo.setFirstimage(firstImageVo == null ? null : firstImageVo.getImgid());
            dataBaseMasterVo.setFirstimage2(firstImageVo == null ? null : firstImageVo.getImgid());
            databaseMasterService.insert(dataBaseMasterVo);

            // green tour - department 생태관광
            String otdId = DepartmentContentVO.OTD_ID_GREENTOUR;
            if (departmentContentService.findOne(newCotId, otdId) == null) {
                departmentContentService.insert(DepartmentContentVO.valueOf(otdId, newCotId));
            }

            // green tour - tag `관광지`로 분류함
            String tagId = ContentTagsVO.TAG_ID_TOURIST;
            if (contentTagsService.findOne(newCotId, tagId) == null) {
                contentTagsService.insert(ContentTagsVO.valueOf(newCotId, tagId));
            }
            return;
        }

        Map<String, Object> common = (Map<String, Object>) item.get("common");

        // NOTE. `detailCommon`  API를 통해 유입된 정보의 경우 `withtour` 필드를 `master`에서 확인할 수 없다.
        //       이런 경우에는 아래 판단으로 처리하도록 한다.
        if (master.get("withtour") == null) {
            // 이 경우에는 무장애로 간주한다.
            if (item.get("withtour") != null) {
                master.setWithTour(true);
            }
        }

        ContentMasterVO content = ContentMasterVO.valueOf(newCotId, master, common);
        contentMasterService.insert(content);

        // image
        List<Map<String, Object>> imageList = (List<Map<String, Object>>) item.get("image");
        if (imageList != null) {
            int index = 1;
            for (Map<String, Object> image : imageList) {
                // NOTE. 썸네일 이미지를 포함하고 있지만, 일단 썸네일은 무시하자!
                //       여기서는 실제 이미지만 취하고(썸네일 제외) 데이터베이스에 등록한다.
                ImageVO imageVo = ImageVO.valueOf(newCotId, image);
                imageVo.setImgid(createImgId()); // Image Id 생성
                imageVo.setImagedescription(content.getTitle() + " " + index ++);
                imageService.insert(imageVo);
            }
        }

        ImageVO firstImageVo = null, firstImage2Vo = null;
        if (common.get("firstimage") != null) {
            String url = common.get("firstimage").toString();

            firstImageVo = new ImageVO();
            firstImageVo.setCotid(newCotId);
            firstImageVo.setUrl(url);
            firstImageVo.setImagedescription(content.getTitle());
            firstImageVo.setIsthubnail(1);

            String imageId = imageService.findOneByCotId(newCotId, url);
            if (imageId == null) {
                firstImageVo.setImgid(createImgId());
                imageService.insert(firstImageVo);
            } else {
                firstImageVo.setImgid(imageId);
            }
        }

        // NOTE. 마스터를 통해 유입된 이미지에도 썸네일이 포함되어 있는데,
        //       이때에는 썸네일을 등록 처리하도록 하자!! (호환성 문제로)
        //       아무튼 이미지 처리는 아직 많은 이슈를 가지고 있다.
        // NOTE. `FIRST_IMAGE2` 는 썸네일 이미지로 현재 사용되고 있지않다. 또한, 액셀 이미지 처리시 삭제 대상이 되기도 하여 무시토록 한다.
        /*
        if (common.get("firstimage2") != null) {
            String url = common.get("firstimage2").toString();

            firstImage2Vo = new ImageVO();
            firstImage2Vo.setCotid(newCotId);
            firstImage2Vo.setUrl(url);
            firstImage2Vo.setImagedescription(content.getTitle());
            firstImage2Vo.setIsthubnail(1);

            String imageId = imageService.findOneByCotId(newCotId, url);
            if (imageId == null) {
                firstImage2Vo.setImgid(createImgId());
                imageService.insert(firstImage2Vo);
            } else {
                firstImage2Vo.setImgid(imageId);
            }
        }
        */

        // NOTE. API를 통해 마스터로 유입된 이미지가 없다면, 상세 이미지중 첫번째를 메인 이미지로 취한다.
        if (firstImageVo == null) {  // || firstImage2Vo == null
            List<ImageVO> list = imageService.findAllByCotId(newCotId);
            if (list != null && !list.isEmpty()) {
                firstImageVo = firstImage2Vo = list.get(0);
            }
        }

        // database master
        DatabaseMasterVO dataBaseMasterVo = DatabaseMasterVO.valueOf(newCotId, common);
        dataBaseMasterVo.setFirstimage(firstImageVo == null ? null : firstImageVo.getImgid());

        // NOTE. `FIRST_IMAGE2` 는 썸네일 이미지로 현재 사용되고 있지않다. 또한, 액셀 이미지 처리시 삭제 대상이 되기도 하여 무시토록 한다.
        // dataBaseMasterVo.setFirstimage2(firstImage2Vo == null ? null : firstImage2Vo.getImgid());
        databaseMasterService.insert(dataBaseMasterVo);

        final int contentTypeId = master.getContentTypeId();
        // intro
        Map<String, Object> introMap = (Map<String, Object>) item.get("intro");
        switch (contentTypeId) {
            case Master.TYPE_TOURIST:
                introService.insertTouristIntro(TouristIntroVO.valueOf(newCotId, introMap));
                break;
            case Master.TYPE_CULTURAL:
                introService.insertCulturalIntro(CulturalIntroVO.valueOf(newCotId, introMap));
                break;
            case Master.TYPE_FESTIVAL:
                introService.insertFestivalIntro(FestivalIntroVO.valueOf(newCotId, introMap));
                break;
            case Master.TYPE_COURSE:
                introService.insertCourseIntro(CourseIntroVO.valueOf(newCotId, introMap));
                break;
            case Master.TYPE_LEPORTS:
                introService.insertLeportsIntro(LeportsIntroVO.valueOf(newCotId, introMap));
                break;
            case Master.TYPE_ACCOMMODATION:
                introService.insertAccommodationIntro(AccommodationIntroVO.valueOf(newCotId, introMap));
                break;
            case Master.TYPE_SHOPPING:
                introService.insertShoppingIntro(ShoppingIntroVO.valueOf(newCotId, introMap));
                break;
            case Master.TYPE_EATERY:
                introService.insertEateryIntro(EateryIntroVO.valueOf(newCotId, introMap));
                break;
        }

        // info
        List<Map<String, Object>> infoList = (List<Map<String, Object>>) item.get("info");
        if (infoList != null) {
            switch (contentTypeId) {
                case Master.TYPE_COURSE: {
                    List<CourseInfoVO> list = new ArrayList<>(infoList.size());
                    for (Map<String, Object> i : infoList) {
                        String imgId = null, subDetailImgUrl = Utils.valueString(i, "subdetailimg");
                        if (!subDetailImgUrl.isEmpty()) {
                            ImageVO imageVo = new ImageVO();
                            imageVo.setImgid(createImgId());
                            imageVo.setCotid(newCotId);
                            imageVo.setUrl(subDetailImgUrl);
                            imageVo.setImagedescription(content.getTitle());
                            imageService.insert(imageVo);

                            imgId = imageVo.getImgid();
                        }

                        CourseInfoVO infoVO = CourseInfoVO.valueOf(newCotId, i);
                        infoVO.setSubdetailimg(imgId);

                        list.add(infoVO);
                    }
                    infoService.insertCourseInfoList(list);
                    break;
                }
                case Master.TYPE_ACCOMMODATION: {
                    List<AccommodationInfoVO> list = new ArrayList<>(infoList.size());
                    String[] roomImgKeys = new String[] { "roomimg1", "roomimg2", "roomimg3", "roomimg4", "roomimg5" };
                    String[] roomImgAlts = new String[] { "roomimg1alt", "roomimg2alt", "roomimg3alt", "roomimg4alt", "roomimg5alt" };
                    ArrayList<String> imageIdList = new ArrayList<>();
                    for (Map<String, Object> i : infoList) {

                        imageIdList.clear();

                        // room images
                        int index = 0;
                        for (String key : roomImgKeys) {
                            String roomImgUrl = Utils.valueString(i, key);
                            if (!roomImgUrl.isEmpty()) {
                                ImageVO imageVo = new ImageVO();
                                imageVo.setImgid(createImgId());
                                imageVo.setCotid(newCotId);
                                imageVo.setUrl(roomImgUrl);
                                imageVo.setImagedescription(Utils.valueString(i, roomImgAlts[index ++]));
                                imageService.insert(imageVo);

                                // cache image id
                                imageIdList.add(imageVo.getImgid());
                            }
                        }

                        // set image id
                        AccommodationInfoVO infoVO = AccommodationInfoVO.valueOf(newCotId, i);
                        for (int t = 0; t < imageIdList.size(); t ++) {
                            switch (t) {
                                case 0:
                                    infoVO.setRoomimg1(imageIdList.get(0));
                                    break;
                                case 1:
                                    infoVO.setRoomimg2(imageIdList.get(1));
                                    break;
                                case 2:
                                    infoVO.setRoomimg3(imageIdList.get(2));
                                    break;
                                case 3:
                                    infoVO.setRoomimg4(imageIdList.get(3));
                                    break;
                                case 4:
                                    infoVO.setRoomimg5(imageIdList.get(4));
                                    break;
                            }
                        }

                        // info list
                        list.add(infoVO);
                    }
                    infoService.insertAccommodationInfoList(list);
                    break;
                }
                default: {
                    List<DetailInfoVO> list = new ArrayList<>(infoList.size());
                    for (Map<String, Object> i : infoList) {
                        list.add(DetailInfoVO.valueOf(newCotId, i));
                    }
                    infoService.insertDetailInfoList(list);
                }
            }
        }

        // 부서 처리
        // department 무장애관광
        if (master.isWithTour()) {
            infoService.deleteDetailInfoWithTour(newCotId);
            infoService.insertDetailInfoList(DetailWithTourVO.valueListOf(newCotId, (Map<String, Object>) item.get("withtour")));

            String otdId = DepartmentContentVO.OTD_ID_WITHTOUR;
            if (departmentContentService.findOne(newCotId, otdId) == null) {
                departmentContentService.insert(DepartmentContentVO.valueOf(otdId, newCotId));
            }
        }

        // department 한국관광품질인증
        if (content.getTitle().matches("(?:.*한국관광\\s*품질인증.*)|(?:.*Korea Quality.*)")) {
            String otdId = DepartmentContentVO.OTD_ID_KOREA_QUALITY;
            if (departmentContentService.findOne(newCotId, otdId) == null) {
                departmentContentService.insert(DepartmentContentVO.valueOf(otdId, newCotId));
            }
        }

        // department 산업관광
        if (dataBaseMasterVo.getCat2().equals("A0204")) {
            String otdId = DepartmentContentVO.OTD_ID_INDUSTRYTOUR;
            if (departmentContentService.findOne(newCotId, otdId) == null) {
                departmentContentService.insert(DepartmentContentVO.valueOf(otdId, newCotId));
            }
        }

        // 태그 처리
        // tag
        String tagId = null;
        switch (dataBaseMasterVo.getCat1()) {
            case "A01":
            case "A02":  // 관광지
                tagId = ContentTagsVO.TAG_ID_TOURIST;
                break;
            case "A03":  // 레포츠
                tagId = ContentTagsVO.TAG_ID_LEPORTS;
                break;
            case "A04":  // 쇼핑
                tagId = ContentTagsVO.TAG_ID_SHOPPING;
                break;
            case "A05":  // 음식
                tagId = ContentTagsVO.TAG_ID_EATERY;
                break;
            case "B02":  // 숙박
                tagId = ContentTagsVO.TAG_ID_ACCOMMODATION;
                break;
            case "C01":  // 추천코스
                tagId = ContentTagsVO.TAG_ID_COURSE;
                break;
        }

        if (tagId != null && contentTagsService.findOne(newCotId, tagId) == null) {
            contentTagsService.insert(ContentTagsVO.valueOf(newCotId, tagId));
        }
    }

    @SuppressWarnings("unchecked")
    private void update(String oldCotId, Map<String, Object> item) {
        Master master = Master.wrap((Map<String, Object>) item.get("master"));
        logger.info(":::UPDATE:::{}", master);
        if (master.isGreenTour()) {
            GreenMaster greenMaster = (GreenMaster) master;

            // green tour - image
            ImageVO firstImageVo = null;
            if (greenMaster.get("firstimage") != null) {
                String url = greenMaster.get("firstimage").toString();

                firstImageVo = new ImageVO();
                firstImageVo.setCotid(oldCotId);
                firstImageVo.setUrl(url);
                firstImageVo.setImagedescription(greenMaster.getTitle());
                firstImageVo.setIsthubnail(1);

                String imageId = imageService.findOneByCotId(oldCotId, url);
                if (imageId == null) {
                    firstImageVo.setImgid(createImgId());
                    imageService.insert(firstImageVo);
                } else {
                    // 이미지가 존재하므로 스킵
                    firstImageVo.setImgid(imageId);
                }
            }

            // green tour master (content_master)
            ContentMasterVO content = ContentMasterVO.valueOf(oldCotId, greenMaster);
            contentMasterService.update(content);

            // green tour master (database_master)
            DatabaseMasterVO dataBaseMasterVo = DatabaseMasterVO.valueOf(oldCotId, greenMaster);
            dataBaseMasterVo.setFirstimage(firstImageVo == null ? null : firstImageVo.getImgid());

            // NOTE. `FIRST_IMAGE2` 는 썸네일 이미지로 현재 사용되고 있지않다. 또한, 액셀 이미지 처리시 삭제 대상이 되기도 하여 무시토록 한다.
            // dataBaseMasterVo.setFirstimage2(firstImageVo == null ? null : firstImageVo.getImgid());
            if (databaseMasterService.findOne(oldCotId) == null) {
                databaseMasterService.insert(dataBaseMasterVo);
            } else {
                databaseMasterService.update(dataBaseMasterVo);
            }

            // department 생태관광
            String otdId = DepartmentContentVO.OTD_ID_GREENTOUR;
            if (departmentContentService.findOne(oldCotId, otdId) == null) {
                departmentContentService.insert(DepartmentContentVO.valueOf(otdId, oldCotId));
            }

            // green tour - tag `관광지`로 분류함
            String tagId = ContentTagsVO.TAG_ID_TOURIST;
            if (contentTagsService.findOne(oldCotId, tagId) == null) {
                contentTagsService.insert(ContentTagsVO.valueOf(oldCotId, tagId));
            }
            return;
        }

        Map<String, Object> common = (Map<String, Object>) item.get("common");

        // NOTE. `detailCommon`  API를 통해 유입된 정보의 경우 `withtour` 필드를 `master`에서 확인할 수 없다.
        //       이런 경우에는 아래 판단으로 처리하도록 한다.
        if (master.get("withtour") == null) {
            // 이 경우에는 무장애로 간주한다.
            if (item.get("withtour") != null) {
                master.setWithTour(true);
            }
        }

        ContentMasterVO content = ContentMasterVO.valueOf(oldCotId, master, common);
        contentMasterService.update(content);

        // image
        List<Map<String, Object>> imageList = (List<Map<String, Object>>) item.get("image");
        if (imageList != null) {
            int index = 1;
            for (Map<String, Object> image : imageList) {
                // NOTE. 썸네일 이미지를 포함하고 있지만, 일단 썸네일은 무시하자!
                //       여기서는 실제 이미지만 취하고(썸네일 제외) 데이터베이스에 등록한다.
                ImageVO imageVo = ImageVO.valueOf(oldCotId, image);
                if (imageService.findOneByCotId(oldCotId, imageVo.getUrl()) == null) {
                    imageVo.setImgid(createImgId()); // Image Id 생성
                    imageVo.setImagedescription(content.getTitle() + " " + index ++);
                    imageService.insert(imageVo);
                }

                // NOTE. 이미지가 이미 존재하는 경우에는 `SKIP` 한다.
            }
        }

        ImageVO firstImageVo = null, firstImage2Vo = null;
        if (common.get("firstimage") != null) {
            String url = common.get("firstimage").toString();

            firstImageVo = new ImageVO();
            firstImageVo.setCotid(oldCotId);
            firstImageVo.setUrl(url);
            firstImageVo.setImagedescription(content.getTitle());
            firstImageVo.setIsthubnail(1);

            String imageId = imageService.findOneByCotId(oldCotId, url);
            if (imageId == null) {
                firstImageVo.setImgid(createImgId());
                imageService.insert(firstImageVo);
            } else {
                firstImageVo.setImgid(imageId);
            }
        }

        // NOTE. 마스터를 통해 유입된 이미지에도 썸네일이 포함되어 있는데,
        //       이때에는 썸네일을 등록 처리하도록 하자!! (호환성 문제로)
        //       아무튼 이미지 처리는 아직 많은 이슈를 가지고 있다.
        // NOTE. `FIRST_IMAGE2` 는 썸네일 이미지로 현재 사용되고 있지않다. 또한, 액셀 이미지 처리시 삭제 대상이 되기도 하여 무시토록 한다.
        /*
        if (common.get("firstimage2") != null) {
            String url = common.get("firstimage2").toString();

            firstImage2Vo = new ImageVO();
            firstImage2Vo.setCotid(oldCotId);
            firstImage2Vo.setUrl(url);
            firstImage2Vo.setImagedescription(content.getTitle());
            firstImage2Vo.setIsthubnail(1);

            String imageId = imageService.findOneByCotId(oldCotId, url);
            if (imageId == null) {
                firstImage2Vo.setImgid(createImgId());
                imageService.insert(firstImage2Vo);
            } else {
                firstImage2Vo.setImgid(imageId);
            }
        }
        */

        // NOTE. API를 통해 마스터로 유입된 이미지가 없다면, 상세 이미지중 첫번째를 메인 이미지로 취한다.
        if (firstImageVo == null) {  // || firstImage2Vo == null
            List<ImageVO> list = imageService.findAllByCotId(oldCotId);
            if (list != null && !list.isEmpty()) {
                firstImageVo = firstImage2Vo = list.get(0);
            }
        }

        // database master
        DatabaseMasterVO dataBaseMasterVo = DatabaseMasterVO.valueOf(oldCotId, common);
        dataBaseMasterVo.setFirstimage(firstImageVo == null ? null : firstImageVo.getImgid());

        // NOTE. `FIRST_IMAGE2` 는 썸네일 이미지로 현재 사용되고 있지않다. 또한, 액셀 이미지 처리시 삭제 대상이 되기도 하여 무시토록 한다.
        // dataBaseMasterVo.setFirstimage2(firstImage2Vo == null ? null : firstImage2Vo.getImgid());

        // database master
        // NOTE. 업데이트의 경우이지만 아래처럼 등록 메소드가 관련된 이유는 데이터의 무결성이 깨어진 상태에서
        //       불일치가 발생하고 있기 때문에 보정차원에서 추가하게 되었다.
        if (databaseMasterService.findOne(oldCotId) == null) {
            databaseMasterService.insert(dataBaseMasterVo);
        } else {
            databaseMasterService.update(dataBaseMasterVo);
        }

        final int contentTypeId = master.getContentTypeId();
        // intro
        Map<String, Object> introMap = (Map<String, Object>) item.get("intro");
        switch (contentTypeId) {
            case Master.TYPE_TOURIST:
                introService.updateTouristIntro(TouristIntroVO.valueOf(oldCotId, introMap));
                break;
            case Master.TYPE_CULTURAL:
                introService.updateCulturalIntro(CulturalIntroVO.valueOf(oldCotId, introMap));
                break;
            case Master.TYPE_FESTIVAL:
                introService.updateFestivalIntro(FestivalIntroVO.valueOf(oldCotId, introMap));
                break;
            case Master.TYPE_COURSE:
                introService.updateCourseIntro(CourseIntroVO.valueOf(oldCotId, introMap));
                break;
            case Master.TYPE_LEPORTS:
                introService.updateLeportsIntro(LeportsIntroVO.valueOf(oldCotId, introMap));
                break;
            case Master.TYPE_ACCOMMODATION:
                introService.updateAccommodationIntro(AccommodationIntroVO.valueOf(oldCotId, introMap));
                break;
            case Master.TYPE_SHOPPING:
                introService.updateShoppingIntro(ShoppingIntroVO.valueOf(oldCotId, introMap));
                break;
            case Master.TYPE_EATERY:
                introService.updateEateryIntro(EateryIntroVO.valueOf(oldCotId, introMap));
                break;
        }

        // info
        List<Map<String, Object>> infoList = (List<Map<String, Object>>) item.get("info");
        if (infoList != null) {
            switch (contentTypeId) {
                case Master.TYPE_COURSE:
                    infoService.deleteCourseInfo(oldCotId);
                    for (Map<String, Object> i : infoList) {
                        infoService.insertCourseInfo(CourseInfoVO.valueOf(oldCotId, i));
                    }
                    break;
                case Master.TYPE_ACCOMMODATION:
                    infoService.deleteAccommodationInfo(oldCotId);

                    List<AccommodationInfoVO> list = new ArrayList<>(infoList.size());
                    String[] roomImgKeys = new String[] { "roomimg1", "roomimg2", "roomimg3", "roomimg4", "roomimg5" };
                    String[] roomImgAlts = new String[] { "roomimg1alt", "roomimg2alt", "roomimg3alt", "roomimg4alt", "roomimg5alt" };
                    ArrayList<String> imageIdList = new ArrayList<>();
                    for (Map<String, Object> i : infoList) {

                        imageIdList.clear();

                        // room images
                        int index = 0;
                        for (String key : roomImgKeys) {
                            String roomImgUrl = Utils.valueString(i, key);
                            String imgId = imageService.findOneByCotId(oldCotId, roomImgUrl);
                            if (imgId == null) {
                                if (!roomImgUrl.isEmpty()) {
                                    ImageVO imageVo = new ImageVO();
                                    imageVo.setImgid(createImgId());
                                    imageVo.setCotid(oldCotId);
                                    imageVo.setUrl(roomImgUrl);
                                    imageVo.setImagedescription(Utils.valueString(i, roomImgAlts[index ++]));
                                    imageService.insert(imageVo);

                                    // cache image id
                                    imageIdList.add(imageVo.getImgid());
                                }
                            } else {
                                if (!roomImgUrl.isEmpty()) {
                                    ImageVO imageVo = new ImageVO();
                                    imageVo.setImgid(imgId);
                                    imageVo.setCotid(oldCotId);
                                    imageVo.setUrl(roomImgUrl);
                                    imageVo.setImagedescription(Utils.valueString(i, roomImgAlts[index++]));
                                    imageService.update(imageVo);

                                    // cache image id
                                    imageIdList.add(imageVo.getImgid());
                                }
                            }
                        }

                        // set image id
                        AccommodationInfoVO infoVO = AccommodationInfoVO.valueOf(oldCotId, i);
                        for (int t = 0; t < imageIdList.size(); t ++) {
                            switch (t) {
                                case 0:
                                    infoVO.setRoomimg1(imageIdList.get(0));
                                    break;
                                case 1:
                                    infoVO.setRoomimg2(imageIdList.get(1));
                                    break;
                                case 2:
                                    infoVO.setRoomimg3(imageIdList.get(2));
                                    break;
                                case 3:
                                    infoVO.setRoomimg4(imageIdList.get(3));
                                    break;
                                case 4:
                                    infoVO.setRoomimg5(imageIdList.get(4));
                                    break;
                            }
                        }

                        // info list
                        list.add(infoVO);
                    }
                    infoService.insertAccommodationInfoList(list);
                    break;
                default:
                    infoService.deleteDetailInfo(oldCotId);
                    for (Map<String, Object> i : infoList) {
                        infoService.insertDetailInfo(DetailInfoVO.valueOf(oldCotId, i));
                    }
            }
        }

        // NOTE. Department 부서 처리는 신규건에 대해서만 적용하기로 하여 update()에서는 처리하지 아니함.
        //       예외적으로 `무장애관광`에 대한 처리는 하여야 한다.
        // NOTE. 하지만, UPDATE 시에도 아래 로직 적용하기로 함. (2018.11.12)

        // department 무장애관광
        if (master.isWithTour()) {
            infoService.deleteDetailInfoWithTour(oldCotId);
            infoService.insertDetailInfoList(DetailWithTourVO.valueListOf(oldCotId, (Map<String, Object>) item.get("withtour")));

            String otdId = DepartmentContentVO.OTD_ID_WITHTOUR;
            if (departmentContentService.findOne(oldCotId, otdId) == null) {
                departmentContentService.insert(DepartmentContentVO.valueOf(otdId, oldCotId));
            }
        }

        // NOTE. TAG 처리는 신규건에 대해서만 적용하기로 하여 update()에서는 처리하지 아니함.
        // NOTE. 하지만, UPDATE 시에도 아래 로직 적용하기로 함. (2018.11.12)

        // department 한국관광품질인증
        if (content.getTitle().matches("(?:.*한국관광\\s*품질인증.*)|(?:.*Korea Quality.*)")) {
            String otdId = DepartmentContentVO.OTD_ID_KOREA_QUALITY;
            if (departmentContentService.findOne(oldCotId, otdId) == null) {
                departmentContentService.insert(DepartmentContentVO.valueOf(otdId, oldCotId));
            }
        }

        // department 산업관광
        if (dataBaseMasterVo.getCat2().equals("A0204")) {
            String otdId = DepartmentContentVO.OTD_ID_INDUSTRYTOUR;
            if (departmentContentService.findOne(oldCotId, otdId) == null) {
                departmentContentService.insert(DepartmentContentVO.valueOf(otdId, oldCotId));
            }
        }

        // 태그 처리
        // tag
        String tagId = null;
        switch (dataBaseMasterVo.getCat1()) {
            case "A01":
            case "A02":  // 관광지
                tagId = ContentTagsVO.TAG_ID_TOURIST;
                break;
            case "A03":  // 레포츠
                tagId = ContentTagsVO.TAG_ID_LEPORTS;
                break;
            case "A04":  // 쇼핑
                tagId = ContentTagsVO.TAG_ID_SHOPPING;
                break;
            case "A05":  // 음식
                tagId = ContentTagsVO.TAG_ID_EATERY;
                break;
            case "B02":  // 숙박
                tagId = ContentTagsVO.TAG_ID_ACCOMMODATION;
                break;
            case "C01":  // 추천코스
                tagId = ContentTagsVO.TAG_ID_COURSE;
                break;
        }

        if (tagId != null && contentTagsService.findOne(oldCotId, tagId) == null) {
            contentTagsService.insert(ContentTagsVO.valueOf(oldCotId, tagId));
        }
    }
}
