package kr.co.uniess.vk.batch.component.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 무장애 시설 정보
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DetailWithTour {
    @JsonProperty("contentid")
    private long contentId;         // 컨텐트 ID
    private String parking;	        // 주차
    private String route; 	        // 대중교통
    private String publictransport;	// 접근로
    private String ticketoffice;	// 매표소
    private String promotion;	    // 홍보물
    private String wheelchair;	    // 휠체어
    private String exit;	        // 출입통로
    private String elevator;	    // 엘리베이터
    private String restroom;	    // 화장실
    private String auditorium;	    // 관람석
    private String room;	        // 객실
    private String handicapetc;	    // 지체장애 기타상세
    private String braileblock;	    // 점자블록
    private String helpdog;	        // 보조견동반
    private String guidehuman;	    // 안내요원
    private String audioguide;	    // 오디오가이드
    private String bigprint;	    // 큰활자 홍보물
    private String brailepromotion;	// 점자홍보물 및 점자표지판
    private String guidesystem;	    // 유도안내설비
    private String blindhandicapetc;// 시각장애 기타상세
    private String signguide;	    // 수화안내
    private String videoguide;	    // 자막 비디오가이드 및 영상자막안내
    private String hearingroom;	    // 객실
    private String hearinghandicapetc;//청각장애 기타상세
    private String stroller;	    // 유모차
    private String lactationroom;	// 수유실
    private String babysparechair;	// 유아용보조의자
    private String infantsfamilyetc;// 영유아가족 기타상세
}
