package kr.co.uniess.vk.batch.repository.model;

import kr.co.uniess.vk.batch.component.model.GreenMaster;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Map;

@Data
@Alias("database")
public class DatabaseMasterVO {

    // API 데이터와 일치
    private Integer booktour;
    private String homepage;
    private String firstimage;     // reference image Id on `IMAGE` TABLE
    private String firstimage2;    // reference image Id on `IMAGE` TABLE
    private String cat1;
    private String cat2;
    private String cat3;
    private Integer areacode;
    private Integer siguguncode;
    private String addr1;
    private String addr2;
    private String zipcode;
    private String mapx;
    private String mapy;
    private String mlevel;
    private String overview;

    private String cotid;


    // `DATABASE_MASTER` table 에만 존재 하는 컬럼
    // 기존 API Batch에서 확인해야할 사항
    // - 확인결과 설정하지 않고 디폴트 값으로 대체한다.
    private Double wgsx;
    private Double wgsy;
    private String adminname;
    private String admintel;
    private String adminfax;
    private String adminemail;
    private String setstatus;
    private Integer dongcode;

    public static DatabaseMasterVO valueOf(String cotId, Map<String, Object> item) {
        DatabaseMasterVO vo = new DatabaseMasterVO();
        vo.cotid = cotId;
        vo.booktour = Utils.valueInteger(item, "booktour");
        vo.homepage = Utils.valueString(item, "homepage");
        vo.cat1 = Utils.valueString(item, "cat1");
        vo.cat2 = Utils.valueString(item, "cat2");
        vo.cat3 = Utils.valueString(item, "cat3");
        vo.areacode = Utils.valueInteger(item, "areacode");
        vo.siguguncode = Utils.valueInteger(item, "sigungucode");
        vo.addr1 = Utils.valueString(item, "addr1");
        vo.addr2 = Utils.valueString(item, "addr2");
        vo.zipcode = Utils.valueString(item, "zipcode");
        vo.mapx = Utils.valueString(item, "mapx");
        vo.mapy = Utils.valueString(item, "mapy");
        vo.mlevel = Utils.valueString(item, "mlevel");
        vo.overview = Utils.valueString(item, "overview").replaceAll("'", "''");

        // NOTE. `DATABASE_MASTER` 테이블에는 관광지 정보에 해당하는 전화번호가 저장된다.
        vo.admintel = Utils.valueString(item, "tel");
        vo.adminname = Utils.valueString(item, "title");

        if (vo.mapx.trim().equals("") || vo.mapx.trim().equals("null")) {
            vo.mapx = null;
        }
        if (vo.mapy.trim().equals("") || vo.mapy.trim().equals("null")) {
            vo.mapy = null;
        }

        // NOTE. 이미지는 `IMAGE`에 등록후 해당 ID를 등록하여야 한다.
        //       따라서 외부에서 이미지 등록후 ID를 설정해야만 정상적용 된다.
        // vo.firstimage = Utils.valueString(item, "firstimage");
        // vo.firstimage2 = Utils.valueString(item, "firstimage2");

        // NOTE. 나머지 미등록 컬럼에 대해서는 디폴트 값으로 대체되므로 여기서 따로 설정하지 않는다. (종전 로직과 동일)
        return vo;
    }

    public static DatabaseMasterVO valueOf(String cotId, GreenMaster item) {
        DatabaseMasterVO vo = new DatabaseMasterVO();
        vo.cotid = cotId;

        vo.areacode = Utils.valueInteger(item, "areacode");
        vo.siguguncode = Utils.valueInteger(item, "sigungucode");

        vo.addr1 = Utils.valueString(item, "addr");
        vo.addr2 = null; // NOTE. 생태관광의 경우 `addr` -> `addr1`로만 처리하고 `addr2 = null`

        vo.cat1 = Utils.valueString(item, "cat1"); // TODO DB에서 확인됨 하지만 어디에서 갖고옴?
        vo.cat2 = Utils.valueString(item, "cat2"); // TODO DB에서 확인됨 하지만 어디에서 갖고옴?
        vo.cat3 = null; // NOTE. 단순 `NULL`처리됨

        vo.overview = Utils.valueString(item, "overview").replaceAll("'", "''");

        // NOTE. `DATABASE_MASTER` 테이블에는 관광지 정보에 해당하는 전화번호가 저장된다.
        vo.admintel = Utils.valueString(item, "tel");
        vo.adminname = Utils.valueString(item, "telname");

        vo.booktour = null;
        vo.homepage = null;
        vo.zipcode = null;
        vo.mapx = null;
        vo.mapy = null;
        vo.mlevel = null;

        // NOTE. 이미지는 `IMAGE`에 등록후 해당 ID를 등록하여야 한다.
        //       따라서 외부에서 이미지 등록후 ID를 설정해야만 정상적용 된다.
        // vo.firstimage = Utils.valueString(item, "firstimage");
        // vo.firstimage2 = Utils.valueString(item, "firstimage2");

        // NOTE. 나머지 미등록 컬럼에 대해서는 디폴트 값으로 대체되므로 여기서 따로 설정하지 않는다. (종전 로직과 동일)
        return vo;
    }
}
