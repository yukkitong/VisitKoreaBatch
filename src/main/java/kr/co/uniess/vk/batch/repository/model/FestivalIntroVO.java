package kr.co.uniess.vk.batch.repository.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Map;

@Data
@Alias("festival")
public class  FestivalIntroVO {
     private String cotId;
     private String scale;
     private String spendTime;
     private String ageLimit;
     private String bookingPlace;
     private String discountInfo;
     private String eventEndDate;
     private String eventHomepage;
     private String eventPlace;
     private String eventStartDate;
     private String festivalGrade;
     private String placeInfo;
     private String playTime;
     private String program;
     private String sponsor1;
     private String sponsor1Tel;
     private String sponsor2;
     private String sponsor2Tel;
     private String subEvent;
     private String useFee;

    public static FestivalIntroVO valueOf(String cotId, Map<String, Object> map) {
        FestivalIntroVO vo = new FestivalIntroVO();
        vo.cotId = cotId;
        // TODO
        return vo;
    }
}
