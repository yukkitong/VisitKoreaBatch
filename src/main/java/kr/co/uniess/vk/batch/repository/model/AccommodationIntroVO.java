package kr.co.uniess.vk.batch.repository.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Map;

@Data
@Alias("accommodation")
public class AccommodationIntroVO {
    private String cotid;
    private String accomcount;
    private String benikia;
    private String checkintime;
    private String checkouttime;
    private String chkcooking;
    private String foodplace;
    private String goodstay;
    private String hanok;
    private String contactinfo;
    private String parking;
    private String pickup;
    private String roomcount;
    private String reservation;
    private String reservationurl;
    private String roomtype;
    private String scale;
    private String subfacility;
    private String barbecue;
    private String beauty;
    private String bicycle;
    private Integer compfire;
    private String fitness;
    private String karaoke;
    private String publicbath;
    private String publicpc;
    private String sauna;
    private String seminar;
    private Integer sports;

    public static AccommodationIntroVO valueOf(String cotId, Map<String, Object> item) {
        AccommodationIntroVO vo = new AccommodationIntroVO();
        vo.cotid = cotId;
        vo.accomcount = Utils.valueString(item, "accomcount");
        vo.benikia = Utils.valueString(item, "benikia");
        vo.checkintime = Utils.valueString(item, "checkintime");
        vo.checkouttime = Utils.valueString(item, "checkouttime");
        vo.chkcooking = Utils.valueString(item, "chkcooking");
        vo.foodplace = Utils.valueString(item, "foodplace");
        vo.goodstay = Utils.valueString(item, "goodstay");
        vo.hanok = Utils.valueString(item, "hanok");
        vo.contactinfo = Utils.valueString(item, "contactinfo");
        vo.parking = Utils.valueString(item, "parking");
        vo.pickup = Utils.valueString(item, "pickup");
        vo.roomcount = Utils.valueString(item, "roomcount");
        vo.reservation = Utils.valueString(item, "reservation");
        vo.reservationurl = Utils.valueString(item, "reservationurl");
        vo.roomtype = Utils.valueString(item, "roomtype");
        vo.scale = Utils.valueString(item, "scale");
        vo.subfacility = Utils.valueString(item, "subfacility");
        vo.barbecue = Utils.valueString(item, "barbecue");
        vo.beauty = Utils.valueString(item, "beauty");
        vo.bicycle = Utils.valueString(item, "bicycle");
        vo.compfire = Utils.valueInteger(item, "compfire");
        vo.fitness = Utils.valueString(item, "fitness");
        vo.karaoke = Utils.valueString(item, "karaoke");
        vo.publicbath = Utils.valueString(item, "publicbath");
        vo.publicpc = Utils.valueString(item, "publicpc");
        vo.sauna = Utils.valueString(item, "sauna");
        vo.seminar = Utils.valueString(item, "seminar");
        vo.sports = Utils.valueInteger(item, "sport");
        return vo;
    }
}
