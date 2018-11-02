package kr.co.uniess.vk.batch.repository.model;

import kr.co.uniess.vk.batch.component.model.ApiData;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Map;
import java.util.UUID;

@Data
@Alias("image")
public class ImageVO {

    // API 데이터와 일치
    private String url;

    // `IMAGE` table 에만 존재 하는 컬럼
    private String imageId;     // create
    private String cotId;       // reference
    private String imagePath;
    private String imageDescription;
    private String isThubnail;  // not a typo!!
    private String md5;

    public void createAndSetImageId() {
        if (imageId == null) {
            setImageId(UUID.randomUUID().toString());
        }
    }

    public static ImageVO valueOf(String cotId, ApiData map) {
        ImageVO vo = new ImageVO();
        vo.cotId = cotId;
        // TODO
        vo.url = map.get("originimgurl").toString();
        return vo;
    }
}
