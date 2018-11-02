package kr.co.uniess.vk.batch.repository.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Map;

@Data
@Alias("leports")
public class LeportsIntroVO {
    private String cotId;
    public static LeportsIntroVO valueOf(String cotId, Map<String, Object> map) {
        LeportsIntroVO vo = new LeportsIntroVO();
        vo.cotId = cotId;
        // TODO
        return vo;
    }
}
