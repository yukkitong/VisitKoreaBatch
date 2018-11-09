package kr.co.uniess.vk.batch.repository.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Map;

@Data
@Alias("course")
public class CourseIntroVO {
    private String cotid;
    private String distance;
    private String taketime;
    private String contactinfo;
    private String schedule;
    private String theme;

    public static CourseIntroVO valueOf(String cotId, Map<String, Object> item) {
        CourseIntroVO vo = new CourseIntroVO();
        vo.cotid = cotId;
        vo.distance = Utils.valueString(item, "distance");
        vo.taketime = Utils.valueString(item, "taketime");
        vo.contactinfo = Utils.valueString(item, "infocentertourcourse");
        vo.schedule = Utils.valueString(item, "schedule");
        vo.theme = Utils.valueString(item, "theme");
        return vo;
    }
}
