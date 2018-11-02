package kr.co.uniess.vk.batch.repository.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Map;

@Data
@Alias("course")
public class CourseIntroVO {
    private String cotId;
    public static CourseIntroVO valueOf(String cotId, Map<String, Object> map) {
        CourseIntroVO vo = new CourseIntroVO();
        vo.cotId = cotId;
        // TODO
        return vo;
    }
}
