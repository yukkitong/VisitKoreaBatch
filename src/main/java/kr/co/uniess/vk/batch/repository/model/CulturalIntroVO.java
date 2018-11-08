package kr.co.uniess.vk.batch.repository.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Map;

@Data
@Alias("cultural")
public class CulturalIntroVO {
    private String cotid;
    private String accomcount;
    private String scale;
    private String spendtime;
    private String chkbabycarriage;
    private String chkcreditcard;
    private String chkpet;
    private String discountinfo;
    private String infocenter;
    private String parking;
    private String parkingfee;
    private String restdate;
    private String usefee;
    private String usetime;

    public static CulturalIntroVO valueOf(String cotId, Map<String, Object> item) {
        CulturalIntroVO vo = new CulturalIntroVO();
        vo.cotid = cotId;
        vo.accomcount = Utils.valueString(item, "accomcount");
        vo.scale = Utils.valueString(item, "scale");
        vo.spendtime = Utils.valueString(item, "spendtime");
        vo.chkbabycarriage = Utils.valueString(item, "chkbabycarriage");
        vo.chkcreditcard = Utils.valueString(item, "chkcreditcard");
        vo.chkpet = Utils.valueString(item, "chkpet");
        vo.discountinfo = Utils.valueString(item, "discountinfo");
        vo.infocenter = Utils.valueString(item, "infocenter");
        vo.parking = Utils.valueString(item, "parking");
        vo.parkingfee = Utils.valueString(item, "parkingfee");
        vo.restdate = Utils.valueString(item, "restdate");
        vo.usefee = Utils.valueString(item, "usefee");
        vo.usetime = Utils.valueString(item, "usetime");
        return vo;
    }
}
