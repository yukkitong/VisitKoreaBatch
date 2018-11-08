package kr.co.uniess.vk.batch.repository.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Map;

@Data
@Alias("eatery")
public class EateryIntroVO {
    private String cotid;
    private String chkcreditcard;
    private String discountinfo;
    private String firstmenu;
    private String contactinfo;
    private String kidsfacility;
    private String opendate;
    private String opentime;
    private String packing;
    private String parking;
    private String reservation;
    private String restdate;
    private String scale;
    private String seat;
    private String smoking;
    private String treatmenu;

    public static EateryIntroVO valueOf(String cotId, Map<String, Object> item) {
        EateryIntroVO vo = new EateryIntroVO();
        vo.cotid = cotId;
        vo.chkcreditcard = Utils.valueString(item, "chkcreditcard");
        vo.discountinfo = Utils.valueString(item, "discountinfo");
        vo.firstmenu = Utils.valueString(item, "firstmenu");
        vo.contactinfo = Utils.valueString(item, "contactinfo");
        vo.kidsfacility = Utils.valueString(item, "kidsfacility");
        vo.opendate = Utils.valueString(item, "opendate");
        vo.opentime = Utils.valueString(item, "opentime");
        vo.packing = Utils.valueString(item, "packing");
        vo.parking = Utils.valueString(item, "parking");
        vo.reservation = Utils.valueString(item, "reservation");
        vo.restdate = Utils.valueString(item, "restdate");
        vo.scale = Utils.valueString(item, "scale");
        vo.seat = Utils.valueString(item, "seat");
        vo.smoking = Utils.valueString(item, "smoking");
        vo.treatmenu = Utils.valueString(item, "treatmenu");
        return vo;
    }
}
