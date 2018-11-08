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
        vo.accomcount = Utils.valueString(item, "accomcount");
        vo.chkbabycarriage = Utils.valueString(item, "chkbabycarriage");
        vo.chkcreditcard = Utils.valueString(item, "chkcreditcard");
        vo.chkpet = Utils.valueString(item, "chkpet");
        vo.expagerange = Utils.valueString(item, "expagerange");
        vo.infocenter = Utils.valueString(item, "infocenter");
        vo.openperiod = Utils.valueString(item, "openperiod");
        vo.parkingfee = Utils.valueString(item, "parkingfee");
        vo.parking = Utils.valueString(item, "parking");
        vo.reservation = Utils.valueString(item, "reservation");
        vo.restdate = Utils.valueString(item, "restdate");
        vo.scale = Utils.valueString(item, "scale");
        vo.usefee = Utils.valueString(item, "usefee");
        vo.usetime = Utils.valueString(item, "usetime");
        return vo;
    }
}
