package kr.co.uniess.vk.batch.repository.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Map;

@Data
@Alias("leports")
public class LeportsIntroVO {
    private String cotid;
    private String accomcount;
    private String chkbabycarriage;
    private String chkcreditcard;
    private String chkpet;
    private String expagerange;
    private String infocenter;
    private String openperiod;
    private String parkingfee;
    private String parking;
    private String reservation;
    private String restdate;
    private String scale;
    private String usefee;
    private String usetime;

    public static LeportsIntroVO valueOf(String cotId, Map<String, Object> item) {
        LeportsIntroVO vo = new LeportsIntroVO();
        vo.cotid = cotId;
        vo.accomcount = Utils.valueString(item, "accomcountleports");
        vo.chkbabycarriage = Utils.valueString(item, "chkbabycarriageleports");
        vo.chkcreditcard = Utils.valueString(item, "chkcreditcardleports");
        vo.chkpet = Utils.valueString(item, "chkpetleports");
        vo.expagerange = Utils.valueString(item, "expagerangeleports");
        vo.infocenter = Utils.valueString(item, "infocenterleports");
        vo.openperiod = Utils.valueString(item, "openperiod");
        vo.parkingfee = Utils.valueString(item, "parkingfeeleports");
        vo.parking = Utils.valueString(item, "parkingleports");
        vo.reservation = Utils.valueString(item, "reservation");
        vo.restdate = Utils.valueString(item, "restdateleports");
        vo.scale = Utils.valueString(item, "scaleleports");
        vo.usefee = Utils.valueString(item, "usefeeleports");
        vo.usetime = Utils.valueString(item, "usetimeleports");
        return vo;
    }
}
