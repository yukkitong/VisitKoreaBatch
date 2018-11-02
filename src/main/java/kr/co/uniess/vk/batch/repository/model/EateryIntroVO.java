package kr.co.uniess.vk.batch.repository.model;

import kr.co.uniess.vk.batch.component.model.ApiData;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Map;

@Data
@Alias("eatery")
public class EateryIntroVO {
    private String cotId;
    public static EateryIntroVO valueOf(String cotId, ApiData map) {
        EateryIntroVO vo = new EateryIntroVO();
        vo.cotId = cotId;
        // TODO
        return vo;
    }
}
