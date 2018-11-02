package kr.co.uniess.vk.batch.repository.model;

import kr.co.uniess.vk.batch.component.model.ApiData;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Data
@Alias("withtour")
public class DetailWithTourVO extends DetailInfoVO {

    private final static Map<String, String> MAPPING_TABLE;

    static {
        HashMap<String, String> map = new HashMap<>();
        map.put("parking", "주차");
        map.put("route", "대중교통");
        map.put("publictransport", "접근로");
        map.put("ticketoffice",	"매표소");
        map.put("promotion", "홍보물");
        map.put("wheelchair", "휠체어");
        map.put("exit",	"출입통로");
        map.put("elevator",	"엘리베이터");
        map.put("restroom",	"화장실");
        map.put("auditorium", "관람석");
        map.put("room",	"객실");
        map.put("handicapetc", "지체장애 기타상세");
        map.put("braileblock", "점자블록");
        map.put("helpdog", "보조견동반");
        map.put("guidehuman", "안내요원");
        map.put("audioguide", "오디오가이드");
        map.put("bigprint",	"큰활자 홍보물");
        map.put("brailepromotion", "점자홍보물 및 점자표지판");
        map.put("guidesystem", "유도안내설비");
        map.put("blindhandicapetc",	"시각장애 기타상세");
        map.put("signguide", "수화안내");
        map.put("videoguide", "자막 비디오가이드 및 영상자막안내");
        map.put("hearingroom", "객실");
        map.put("hearinghandicapetc", "청각장애 기타상세");
        map.put("stroller",	"유모차");
        map.put("lactationroom", "수유실");
        map.put("babysparechair", "유아용보조의자");
        map.put("infantsfamilyetc",	"영유아가족 기타상세");

        MAPPING_TABLE = Collections.unmodifiableMap(map);
    }

    public static DetailWithTourVO valueOf(String cotId, ApiData map) {
        DetailWithTourVO vo = new DetailWithTourVO();
        vo.setCotId(cotId);
        vo.setFldGubun(25);
        for (String key : map.keySet()) {
            if (map.containsKey(key)) {
                vo.setDisplayTitle(MAPPING_TABLE.get(key));
                vo.setContentBody(map.get(key).toString());
            }
        }
        return vo;
    }
}
