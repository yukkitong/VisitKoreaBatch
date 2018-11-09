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
        vo.chkcreditcard = Utils.valueString(item, "chkcreditcardfood");
        vo.discountinfo = Utils.valueString(item, "discountinfofood");
        vo.firstmenu = Utils.valueString(item, "firstmenu");
        vo.contactinfo = Utils.valueString(item, "infocenterfood");
        vo.kidsfacility = Utils.valueString(item, "kidsfacility");
        vo.opendate = Utils.valueString(item, "opendatefood");
        vo.opentime = Utils.valueString(item, "opentimefood");
        vo.packing = Utils.valueString(item, "packing");
        vo.parking = Utils.valueString(item, "parkingfood");
        vo.reservation = Utils.valueString(item, "reservationfood");
        vo.restdate = Utils.valueString(item, "restdatefood");
        vo.scale = Utils.valueString(item, "scalefood");
        vo.seat = Utils.valueString(item, "seat");
        vo.smoking = Utils.valueString(item, "smoking");
        vo.treatmenu = Utils.valueString(item, "treatmenu");
        return vo;
    }
}
