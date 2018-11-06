package kr.co.uniess.vk.batch.repository.model;

import kr.co.uniess.vk.batch.component.model.ApiData;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Map;

@Data
@Alias("course_info")
public class CourseInfoVO {

    private String cotId;

    public static CourseInfoVO valueOf(String cotId, Map<String, Object> map) {
        CourseInfoVO vo = new CourseInfoVO();
        vo.cotId = cotId;
        // TODO
        return vo;
    }
}
