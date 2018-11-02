package kr.co.uniess.vk.batch.repository.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Map;

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

    // TODO more ids

    // reference ID on CONTENT_MASTER
    private String cotId;

    // reference ID on OTHER_DEPARTMENT_SERVICE
    private String otdId;

    public static DepartmentContentVO valueOf(String otdId, String cotId) {
        ensureOtdId(otdId);
        DepartmentContentVO vo = new DepartmentContentVO();
        vo.otdId = otdId;
        vo.cotId = cotId;
        return vo;
    }

    private static void ensureOtdId(String otdId) {
        switch (otdId) {
            case OTD_ID_CITYTOUR:
            case OTD_ID_GREENTOUR:
            case OTD_ID_INDUSTRYTOUR:
            case OTD_ID_WITHTOUR:
                break;
            default:
                throw new IllegalArgumentException("You have a wrong OTD_ID. [" + otdId + " ]");
        }
    }
}
