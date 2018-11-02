package kr.co.uniess.vk.batch.repository.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Map;

@Data
@Alias("detail_info")
public class DetailInfoVO {
    private String cotId;
    private Integer fldGubun;
    private String displayTitle;
    private String contentBody;
    private Integer serialNum;

    public static DetailInfoVO valueOf(String cotId, Map<String, Object> map) {
        DetailInfoVO vo = new DetailInfoVO();
        vo.cotId = cotId;
        // TODO
        return vo;
    }
}
