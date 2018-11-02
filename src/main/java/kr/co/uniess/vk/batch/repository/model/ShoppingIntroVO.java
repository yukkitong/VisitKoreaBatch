package kr.co.uniess.vk.batch.repository.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Map;

@Data
@Alias("shopping")
public class ShoppingIntroVO {
    private String cotId;
    public static ShoppingIntroVO valueOf(String cotId, Map<String, Object> map) {
        ShoppingIntroVO vo = new ShoppingIntroVO();
        vo.cotId = cotId;
        // TODO
        return vo;
    }
}
