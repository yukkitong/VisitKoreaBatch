package kr.co.uniess.vk.batch.repository.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("department")
public class DepartmentContentVO {

    /**
     * 생태관광 ID
     */
    public static final String OTD_ID_GREENTOUR = "27f7a2ca-84c4-11e8-8165-020027310001";

    /**
     * 산업관광 ID
     */
    public static final String OTD_ID_INDUSTRYTOUR = "114b23a6-84c4-11e8-8165-020027310001";

    /**
     * 시티투어 ID
     */
    public static final String OTD_ID_CITYTOUR = "81f62fd1-8939-11e8-8165-020027310001";

    /**
     * 무장애관광 ID
     */
    public static final String OTD_ID_WITHTOUR = "b55ffe10-84c3-11e8-8165-020027310001";

    /**
     * 한국관광품질인증 ID
     */
    public static final String OTD_ID_KOREA_QUALITY = "456a84d1-84c4-11e8-8165-020027310001";

    // reference ID on CONTENT_MASTER
    private String cotid;

    // reference ID on OTHER_DEPARTMENT_SERVICE
    private String otdid;

    public static DepartmentContentVO valueOf(String otdId, String cotId) {
        ensureOtdId(otdId);
        DepartmentContentVO vo = new DepartmentContentVO();
        vo.otdid = otdId;
        vo.cotid = cotId;
        return vo;
    }

    private static void ensureOtdId(String otdId) {
        switch (otdId) {
            case OTD_ID_CITYTOUR:
            case OTD_ID_GREENTOUR:
            case OTD_ID_INDUSTRYTOUR:
            case OTD_ID_WITHTOUR:
            case OTD_ID_KOREA_QUALITY:
                break;
            default:
                throw new IllegalArgumentException("You have a wrong OTD_ID. [" + otdId + " ]");
        }
    }
}
