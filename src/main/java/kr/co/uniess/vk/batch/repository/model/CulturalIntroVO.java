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
        vo.accomcount = Utils.valueString(item, "accomcountculture");
        vo.scale = Utils.valueString(item, "scale");
        vo.spendtime = Utils.valueString(item, "spendtime");
        vo.chkbabycarriage = Utils.valueString(item, "chkbabycarriageculture");
        vo.chkcreditcard = Utils.valueString(item, "chkcreditcardculture");
        vo.chkpet = Utils.valueString(item, "chkpetculture");
        vo.discountinfo = Utils.valueString(item, "discountinfo");
        vo.infocenter = Utils.valueString(item, "infocenterculture");
        vo.parking = Utils.valueString(item, "parkingculture");
        vo.parkingfee = Utils.valueString(item, "parkingfee");
        vo.restdate = Utils.valueString(item, "restdateculture");
        vo.usefee = Utils.valueString(item, "usefee");
        vo.usetime = Utils.valueString(item, "usetimeculture");
        return vo;
    }
}
