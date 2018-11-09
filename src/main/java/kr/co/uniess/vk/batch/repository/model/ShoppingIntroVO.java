package kr.co.uniess.vk.batch.repository.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Map;

@Data
@Alias("shopping")
public class ShoppingIntroVO {
    private String cotid;
    private String chkbabycarriage;
    private String chkcreditcard;
    private String chkpet;
    private String culturecenter;
    private String fairday;
    private String contactinfo;
    private String opendate;
    private String opentime;
    private String parking;
    private String restdate;
    private String restroom;
    private String saleitem;
    private String saleitemcost;
    private String scale;
    private String shopguide;

    public static ShoppingIntroVO valueOf(String cotId, Map<String, Object> item) {
        ShoppingIntroVO vo = new ShoppingIntroVO();
        vo.cotid = cotId;
        vo.chkbabycarriage = Utils.valueString(item, "chkbabycarriageshopping");
        vo.chkcreditcard = Utils.valueString(item, "chkcreditcardshopping");
        vo.chkpet = Utils.valueString(item, "chkpetshopping");
        vo.culturecenter = Utils.valueString(item, "culturecenter");
        vo.fairday = Utils.valueString(item, "fairday");
        vo.contactinfo = Utils.valueString(item, "infocentershopping");
        vo.opendate = Utils.valueString(item, "opendateshopping");
        vo.opentime = Utils.valueString(item, "opentime"); // API 에서 `opentimeshopping` 이지 아닐까? 확인 필요 - 확인 결과 `opentime`이 맞음.
        vo.parking = Utils.valueString(item, "parkingshopping");
        vo.restdate = Utils.valueString(item, "restdateshopping");
        vo.restroom = Utils.valueString(item, "restroom");
        vo.saleitem = Utils.valueString(item, "saleitem");
        vo.saleitemcost = Utils.valueString(item, "saleitemcost");
        vo.scale = Utils.valueString(item, "scaleshopping");
        vo.shopguide = Utils.valueString(item, "shopguide");
        return vo;
    }
}
