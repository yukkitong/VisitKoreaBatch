package kr.co.uniess.vk.batch.repository.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Map;

@Data
@Alias("accommodation_info")
public class AccommodationInfoVO {
    private String cotid;
    private String roomcode;
    private String roomtitle;
    private String roomsize1;
    private String roomcount;
    private String roombasecount;
    private String roommaxcount;
    private String roomoffseasonminfee1;
    private String roomoffseasonminfee2;
    private String roompeakseasonminfee1;
    private String roompeakseasonminfee2;
    private String roomintro;
    private String roombathfacility;
    private String roombath;
    private String roomhometheater;
    private String roomairconditioner;
    private String roomtv;
    private String roompc;
    private String roomcable;
    private String roominternet;
    private String roomrefrigerator;
    private String roomtoiletries;
    private String roomsofa;
    private String roomcook;
    private String roomtable;
    private String roomhairdryer;
    private String roomsize2;
    private String roomimg1;
    private String roomimg2;
    private String roomimg3;
    private String roomimg4;
    private String roomimg5;

    public static AccommodationInfoVO valueOf(String cotId, Map<String, Object> item) {
        AccommodationInfoVO vo = new AccommodationInfoVO();
        vo.cotid = cotId;
        vo.roomcode = Utils.valueString(item, "roomcode");
        vo.roomtitle = Utils.valueString(item, "roomtitle");
        vo.roomsize1 = Utils.valueString(item, "roomsize1");
        vo.roomcount = Utils.valueString(item, "roomcount");
        vo.roombasecount = Utils.valueString(item, "roombasecount");
        vo.roommaxcount = Utils.valueString(item, "roommaxcount");
        vo.roomoffseasonminfee1 = Utils.valueString(item, "roomoffseasonminfee1");
        vo.roomoffseasonminfee2 = Utils.valueString(item, "roomoffseasonminfee2");
        vo.roompeakseasonminfee1 = Utils.valueString(item, "roompeakseasonminfee1");
        vo.roompeakseasonminfee2 = Utils.valueString(item, "roompeakseasonminfee2");
        vo.roomintro = Utils.valueString(item, "roomintro");
        vo.roombathfacility = Utils.valueString(item, "roombathfacility");
        vo.roombath = Utils.valueString(item, "roombath");
        vo.roomhometheater = Utils.valueString(item, "roomhometheater");
        vo.roomairconditioner = Utils.valueString(item, "roomairconditioner");
        vo.roomtv = Utils.valueString(item, "roomtv");
        vo.roompc = Utils.valueString(item, "roompc");
        vo.roomcable = Utils.valueString(item, "roomcable");
        vo.roominternet = Utils.valueString(item, "roominternet");
        vo.roomrefrigerator = Utils.valueString(item, "roomrefrigerator");
        vo.roomtoiletries = Utils.valueString(item, "roomtoiletries");
        vo.roomsofa = Utils.valueString(item, "roomsofa");
        vo.roomcook = Utils.valueString(item, "roomcook");
        vo.roomtable = Utils.valueString(item, "roomtable");
        vo.roomhairdryer = Utils.valueString(item, "roomhairdryer");
        vo.roomsize2 = Utils.valueString(item, "roomsize2");

        // TODO IMAGE는 URL이 아닌 IMG_ID 값이 들어가야 한다.
//        vo.roomimg1 = Utils.valueString(item, "roomimg1");
//        vo.roomimg2 = Utils.valueString(item, "roomimg2");
//        vo.roomimg3 = Utils.valueString(item, "roomimg3");
//        vo.roomimg4 = Utils.valueString(item, "roomimg4");
//        vo.roomimg5 = Utils.valueString(item, "roomimg5");
        return vo;
    }
}
