package kr.co.uniess.vk.batch.repository.model;

import kr.co.uniess.vk.batch.component.model.ApiData;
import kr.co.uniess.vk.batch.component.model.Master;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Map;
import java.util.UUID;

@Data
@Alias("content")
public class ContentMasterVO {

    // API 데이터와 일치
    private String contentId;      // reference
    private Integer contentType;
    private String title;
    private String createDate;
    private String modifiedDate;

    // `CONTENT_MASTER` table 에만 존재 하는 컬럼
    // TODO 기존 API Batch에서 확인해야할 사항
    private String cotId;          // create
    private String contentStatus;
    private String displayTitle;
    private String readCount;
    private String showFlag;
    private String createUsrId;
    private String dept;
    private String deptView;
    private String tel;

    public void createAndSetCotId() {
        if (cotId == null) {
            setCotId(UUID.randomUUID().toString());
        }
    }

    public static ContentMasterVO valueOf(Master master) {
        ContentMasterVO vo = new ContentMasterVO();
        // TODO
        return vo;
    }
}
