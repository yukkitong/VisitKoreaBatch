package kr.co.uniess.vk.batch.repository.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Map;

@Data
@Alias("tags")
public class ContentTagsVO {

    /**
     * 관광지 TAG_ID ( CAT1 = A01, A02 )
     */
    public final static String TAG_ID_TOURIST = "3f36ca4b-6f45-45cb-9042-265c96a4868c";

    /**
     * 레포츠 TAG_ID ( CAT1 = A03 )
     */
    public final static String TAG_ID_LEPORTS = "e6875575-2cc2-43ba-9651-28d31a7b3e23";

    /**
     * 쇼핑 TAG_ID ( CAT1 = A04 )
     */
    public final static String TAG_ID_SHOPPING = "0f29b431-75ac-4ab4-a892-b247d516b31d"; // TODO `DOC` 수정 TAG_ID TYPO

    /**
     * 음식 TAG_ID ( CAT1 = A05 )
     */
    public final static String TAG_ID_EATERY = "11751b64-5bf9-44fa-90cd-e0e1b092caf6";

    /**
     * 숙박 TAG_ID ( CAT1 = B02 )
     */
    public final static String TAG_ID_ACCOMMODATION = "b7023aff-8138-4a00-ae7f-e4fe7b13a61b";

    /**
     * 추천코스 TAG_ID ( CAT1 = C01 )
     */
    public final static String TAG_ID_COURSE = "25e9e811-96e1-11e8-8165-020027310001";

    private String tagid;
    private String cotid;
    private String mastertag;

    public static ContentTagsVO valueOf(Map<String, Object> item) {
        ensureTagId(Utils.valueString(item, "tagid"));

        ContentTagsVO vo = new ContentTagsVO();
        vo.tagid = Utils.valueString(item, "tagid");
        vo.cotid = Utils.valueString(item, "cotid");
        vo.mastertag = Utils.valueString(item, "mastertag");
        return vo;
    }

    public static ContentTagsVO valueOf(String cotid, String tagid) {
        ensureTagId(tagid);

        ContentTagsVO vo = new ContentTagsVO();
        vo.tagid = tagid;
        vo.cotid = cotid;
        vo.mastertag = null;
        return vo;
    }

    /**
     * Tour API 배치에서의 로직으로 일반적이지는 않다.
     * 일반적으로 사용하고자 한다면 아래 코드는 필요없을 것이다.
     * @param tagId
     */
    private static void ensureTagId(String tagId) {
        switch (tagId) {
            case TAG_ID_TOURIST:
            case TAG_ID_LEPORTS:
            case TAG_ID_SHOPPING:
            case TAG_ID_EATERY:
            case TAG_ID_ACCOMMODATION:
            case TAG_ID_COURSE:
                break;
            default:
                throw new IllegalArgumentException("You have a wrong TAG_ID. [" + tagId + " ]");
        }
    }
}
