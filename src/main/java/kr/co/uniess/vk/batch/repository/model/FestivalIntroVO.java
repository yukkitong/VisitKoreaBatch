package kr.co.uniess.vk.batch.repository.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Map;

@Data
@Alias("festival")
public class  FestivalIntroVO {
     private String cotid;
     private String scale;
     private String spendtime;
     private String agelimit;
     private String bookingplace;
     private String discountinfo;
     private String eventenddate;
     private String eventhomepage;
     private String eventplace;
     private String eventstartdate;
     private String festivalgrade;
     private String placeinfo;
     private String playtime;
     private String program;
     private String sponsor1;
     private String sponsor1tel;
     private String sponsor2;
     private String sponsor2tel;
     private String subevent;
     private String usefee;

    public static FestivalIntroVO valueOf(String cotId, Map<String, Object> item) {
        FestivalIntroVO vo = new FestivalIntroVO();
        vo.cotid = cotId;
        vo.scale = Utils.valueString(item, "scale");
        vo.spendtime = Utils.valueString(item, "spendtimefestival");
        vo.agelimit = Utils.valueString(item, "agelimit");
        vo.bookingplace = Utils.valueString(item, "bookingplace");
        vo.discountinfo = Utils.valueString(item, "discountinfofestival");
        vo.eventenddate = Utils.valueString(item, "eventenddate");
        vo.eventhomepage = Utils.valueString(item, "eventhomepage");
        vo.eventplace = Utils.valueString(item, "eventplace");
        vo.eventstartdate = Utils.valueString(item, "eventstartdate");
        vo.festivalgrade = Utils.valueString(item, "festivalgrade");
        vo.placeinfo = Utils.valueString(item, "placeinfo");
        vo.playtime = Utils.valueString(item, "playtime");
        vo.program = Utils.valueString(item, "program");
        vo.sponsor1 = Utils.valueString(item, "sponsor1");
        vo.sponsor1tel = Utils.valueString(item, "sponsor1tel");
        vo.sponsor2 = Utils.valueString(item, "sponsor2");
        vo.sponsor2tel = Utils.valueString(item, "sponsor2tel");
        vo.subevent = Utils.valueString(item, "subevent");
        vo.usefee = Utils.valueString(item, "usetimefestival");
        return vo;
    }
}
