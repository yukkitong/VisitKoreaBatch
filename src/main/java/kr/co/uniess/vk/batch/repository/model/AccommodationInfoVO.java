package kr.co.uniess.vk.batch.repository.model;

import kr.co.uniess.vk.batch.component.model.ApiData;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Map;

@Data
@Alias("accommodation_info")
public class AccommodationInfoVO {
    private String cotId;

    public static AccommodationInfoVO valueOf(String cotId, ApiData map) {
        AccommodationInfoVO vo = new AccommodationInfoVO();
        vo.cotId = cotId;
        // TODO
        return vo;
    }
}
