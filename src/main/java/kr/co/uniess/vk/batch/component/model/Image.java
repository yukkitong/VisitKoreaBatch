package kr.co.uniess.vk.batch.component.model;

import lombok.Data;

@Data
public class Image {
    private String contentid;	    // 콘텐츠ID
    private String imgname;	        // 이미지명
    private String originimgurl;	// 원본 이미지
    private String smallimageurl;	// 썸네일 이미지
    private String serialnum;	    // 이미지 일련번호
}
