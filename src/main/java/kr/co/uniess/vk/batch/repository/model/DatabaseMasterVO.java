package kr.co.uniess.vk.batch.repository.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Map;

@Data
@Alias("database")
public class DatabaseMasterVO {

    // API 데이터와 일치
    private Integer bookTour;
    private String homepage;
    private String firstImage;     // reference image Id on `IMAGE` TABLE
    private String firstImage2;    // reference image Id on `IMAGE` TABLE
    private String cat1;
    private String cat2;
    private String cat3;
    private Integer areaCode;
    private Integer sigugunCode;
    private String addr1;
    private String addr2;
    private String zipCode;
    private String mapX;
    private String mapY;
    private String mLevel;
    private String overview;

    // `DATABASE_MASTER` table 에만 존재 하는 컬럼
    // TODO 기존 API Batch에서 확인해야할 사항
    private String cotId;          // reference
    private Double wgsX;
    private Double wgsY;
    private String adminName;
    private String adminTel;
    private String adminFax;
    private String adminEmail;
    private String setStatus;
    private Integer dongCode;

    public static DatabaseMasterVO valueOf(Map<String, Object> map) {
        DatabaseMasterVO vo = new DatabaseMasterVO();
        // TODO
        return vo;
    }
}
