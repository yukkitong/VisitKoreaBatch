package kr.co.uniess.vk.batch.repository.model;

import kr.co.uniess.vk.batch.component.model.ApiData;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Map;

@Data
@Alias("tourist")
public class TouristIntroVO {
    private String cotId;
    private String accomCount;
    private String chkBabyCarriage;
    private String chkCreditCard;
    private String chkPet;
    private String expAgeRange;
    private String expGuide;
    private String heritage1;
    private String heritage2;
    private String heritage3;
    private String infoCenter;
    private String openDate;
    private String parking;
    private String restDate;
    private String useSeason;
    private String useTime;

    public static TouristIntroVO valueOf(String cotId, ApiData map) {
        TouristIntroVO vo = new TouristIntroVO();
        vo.cotId = cotId;
        // TODO
        return vo;
    }
}
