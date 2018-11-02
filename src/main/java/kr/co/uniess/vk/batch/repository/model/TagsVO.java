package kr.co.uniess.vk.batch.repository.model;

import kr.co.uniess.vk.batch.component.model.ApiData;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Map;

@Data
@Alias("tags")
public class TagsVO {

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
    public final static String TAG_ID_SHOPPING = "0f29b431-75ac-4ab4-a892-b247d516b31";

    /**
     * 음식 TAG_ID ( CAT1 = A05 )
     */
    public final static String TAG_ID_EATERY = "11751b64-5bf9-44fa-90cd-e0e1b092caf6";

    /**
     * 숙박 TAG_ID ( CAT1 = B02 )
     */
    public final static String TAG_ID_ACCOMMODATION = "b7023aff-8138-4a00-ae7f-e4fe7b13a61b";

    /**
     * 코스 TAG_ID ( CAT1 = C01 )
     */
    public final static String TAG_ID_COURSE = "8ddf2f14-a1c7-11e8-8165-020027310001";


    public static TagsVO valueOf(ApiData map) {
        TagsVO vo = new TagsVO();
        // TODO
        return vo;
    }

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
