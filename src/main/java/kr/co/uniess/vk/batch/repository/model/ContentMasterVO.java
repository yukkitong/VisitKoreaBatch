package kr.co.uniess.vk.batch.repository.model;

import kr.co.uniess.vk.batch.component.model.Master;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Date;
import java.util.Map;

@Data
@Alias("content")
public class ContentMasterVO {

    // API 데이터와 일치
    private String contentid;      // reference
    private Integer contenttypeid;
    private String title;
//    private Date createdate;
//    private Date modifieddate;

    private String cotid;          // create
    private String displaytitle;
    private Integer readcount;

    // `CONTENT_MASTER` table 에만 존재 하는 컬럼
    // TODO 기존 API Batch에서 확인해야할 사항
    private String showflag;
    private String contentstatus;
    private String createusrid;
    private String dept;
    private String deptview;
    private String tel;

    public static ContentMasterVO valueOf(String cotId, Master master, Map<String, Object> common) {
        ContentMasterVO vo = new ContentMasterVO();
        vo.cotid = cotId;
        vo.contentid = master.getContentId();
        vo.contenttypeid = master.getContentTypeId();
        vo.readcount = master.getReadCount();

        vo.title = common.get("title").toString();
        vo.displaytitle = common.get("title").toString();

        // TODO
        return vo;
    }
}
