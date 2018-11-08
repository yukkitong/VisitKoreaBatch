package kr.co.uniess.vk.batch.repository.model;

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
    private String imageid;     // create
    private String cotid;       // reference
    private String imagepath;
    private String imagedescription;
    private Integer isthubnail;  // not a typo!!
    private String md5;

    public void createAndSetImageId() {
        if (imageid == null) {
            setImageid(UUID.randomUUID().toString());
        }
    }

    public static ImageVO valueOf(String cotId, Map<String, Object> map) {
        ImageVO vo = new ImageVO();
        vo.cotid = cotId;
        vo.url = map.get("originimgurl").toString();
        vo.isthubnail = 0;
        return vo;
    }

    public void setDescription(String desc) {
        imagedescription = desc;
    }
}
