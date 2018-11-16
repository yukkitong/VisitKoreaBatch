package kr.co.uniess.vk.batch.repository.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Map;

@Data
@Alias("tourist")
public class TouristIntroVO {
    private String cotid;
    private String accomcount;
    private String chkbabycarriage;
    private String chkcreditcard;
    private String chkpet;
    private String expagerange;
    private String expguide;
    private Integer heritage1;
    private Integer heritage2;
    private Integer heritage3;
    private String infocenter;
    private String opendate;
    private String parking;
    private String restdate;
    private String useseason;
    private String usetime;

    public static TouristIntroVO valueOf(String cotId, Map<String, Object> item) {
        TouristIntroVO vo = new TouristIntroVO();
        vo.cotid = cotId;
        vo.accomcount = Utils.valueString(item, "accomcount");
        vo.chkbabycarriage = Utils.valueString(item, "chkbabycarriage");
        vo.chkcreditcard = Utils.valueString(item, "chkcreditcard");
        vo.chkpet = Utils.valueString(item, "chkpet");
        vo.expagerange = Utils.valueString(item, "expagerange");
        vo.expguide = Utils.valueString(item, "expguide");
        vo.heritage1 = Utils.valueInteger(item, "heritage1");
        vo.heritage2 = Utils.valueInteger(item, "heritage2");
        vo.heritage3 = Utils.valueInteger(item, "heritage3");
        vo.infocenter = Utils.valueString(item, "infocenter");
        vo.opendate = Utils.valueString(item, "opendate");
        vo.parking = Utils.valueString(item, "parking");
        vo.restdate = Utils.valueString(item, "restdate");
        vo.useseason = Utils.valueString(item, "useseason");
        vo.usetime = Utils.valueString(item, "usetime");
        return vo;
    }
}
