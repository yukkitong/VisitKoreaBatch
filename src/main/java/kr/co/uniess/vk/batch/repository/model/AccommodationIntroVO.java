package kr.co.uniess.vk.batch.repository.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Map;

@Data
@Alias("accommodation")
public class AccommodationIntroVO {
    private String cotId;
    public static AccommodationIntroVO valueOf(String cotId, Map<String, Object> map) {
        AccommodationIntroVO vo = new AccommodationIntroVO();
        vo.cotId = cotId;
        // TODO
        return vo;
    }
}
