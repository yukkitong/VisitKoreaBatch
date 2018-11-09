package kr.co.uniess.vk.batch.repository.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Map;

@Data
@Alias("course_info")
public class CourseInfoVO {

    private String cotid;
    private String subcontentid;
    private String subdetailimg;
    private String subdetailoverview;
    private String subname;
    private String subnum;

    public static CourseInfoVO valueOf(String cotId, Map<String, Object> item) {
        CourseInfoVO vo = new CourseInfoVO();
        vo.cotid = cotId;
        vo.subcontentid = Utils.valueString(item, "subcontentid");
        vo.subdetailoverview = Utils.valueString(item, "subdetailoverview");
        vo.subname = Utils.valueString(item, "subname");
        vo.subnum = Utils.valueString(item, "subnum");

        // NOTE. IMAGE는 URL이 아닌 IMG_ID 값이 들어가야 한다.
        // vo.subdetailimg = Utils.valueString(item, "subdetailimg");
        return vo;
    }
}
