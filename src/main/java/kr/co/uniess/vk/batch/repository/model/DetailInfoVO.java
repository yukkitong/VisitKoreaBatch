package kr.co.uniess.vk.batch.repository.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Map;

@Data
@Alias("detail_info")
public class DetailInfoVO {
    private String cotid;
    private Integer fldgubun;
    private String displaytitle;
    private String contentbody;
    private Integer serialnum;

    public static DetailInfoVO valueOf(String cotId, Map<String, Object> item) {
        DetailInfoVO vo = new DetailInfoVO();
        vo.cotid = cotId;
        vo.fldgubun = Utils.valueInteger(item, "fldgubun");
        vo.displaytitle = Utils.valueString(item, "infoname");
        vo.contentbody = Utils.valueString(item, "infotext");
        vo.serialnum = Utils.valueInteger(item, "serialnum");
        return vo;
    }
}
