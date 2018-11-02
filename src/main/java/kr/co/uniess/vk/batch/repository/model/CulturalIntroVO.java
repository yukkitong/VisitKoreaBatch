package kr.co.uniess.vk.batch.repository.model;

import kr.co.uniess.vk.batch.component.model.ApiData;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Map;

@Data
@Alias("cultural")
public class CulturalIntroVO {
    private String cotId;
    private String accomCount;
    private String scale;
    private String spendTime;
    private String chkBabyCarriage;
    private String chkCreditCard;
    private String chkPet;
    private String disCountInfo;
    private String infoCenter;
    private String parking;
    private String parkingFee;
    private String restDate;
    private String useFee;
    private String useTime;

    public static CulturalIntroVO valueOf(String cotId, ApiData map) {
        CulturalIntroVO vo = new CulturalIntroVO();
        vo.cotId = cotId;
        // TODO
        return vo;
    }
}
