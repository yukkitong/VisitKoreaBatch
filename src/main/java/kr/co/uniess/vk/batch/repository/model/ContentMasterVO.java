package kr.co.uniess.vk.batch.repository.model;

import kr.co.uniess.vk.batch.component.model.GreenMaster;
import kr.co.uniess.vk.batch.component.model.Master;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Date;
import java.util.Map;

@Data
@Alias("content")
public class ContentMasterVO {

    // API 데이터와 일치
    private String contentid;
    private Integer contenttypeid;
    private String title;

    // 아래 날짜 정보는 등록, 수정시 NOW() 함수 이용하도록 한다.
    // private Date createdate;
    // private Date modifieddate;

    private String cotid;
    private String displaytitle;
    private Integer readcount;

    // `CONTENT_MASTER` table 에만 존재 하는 컬럼
    // 기존 API Batch에서 확인해야할 사항
    // - 확인결과 하드코딩된 상수값으로 되어있어 동일한 방법으로 처리한다.
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

        setDefaultValue(vo);
        return vo;
    }

    public static ContentMasterVO valueOf(String cotId, GreenMaster master) {
        ContentMasterVO vo = new ContentMasterVO();
        vo.cotid = cotId;
        vo.contentid = master.getContentId();
        vo.contenttypeid = master.getContentTypeId();
        vo.readcount = master.getReadCount();

        vo.title = master.getTitle();
        vo.displaytitle = master.getTitle();

        setDefaultValue(vo);
        return vo;
    }

    // 종전의 로직과 동일: 하드코딩된 상수값으로 되어있어 동일한 방법으로 처리한다.
    private static void setDefaultValue(ContentMasterVO vo) {
        vo.showflag = "1";
        vo.contentstatus = "2";
        vo.createusrid = "4e512b8a-60ad-11e8-8cca-00232456a22f"; // who?
        vo.dept = "국내 온라인 홍보팀";
        vo.deptview = "국내 온라인 홍보팀";
        // NOTE. `CONTENT_MASTER` 테이블에는 원주 전화번호
        vo.tel = "033-738-3588";
    }
}
