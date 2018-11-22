package kr.co.uniess.vk.batch.component.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Master extends HashMap<String, Object> {

    /**
     * CONTENT TYPE IDs
     */
    public static final int TYPE_TOURIST = 12;
    public static final int TYPE_CULTURAL = 14;
    public static final int TYPE_FESTIVAL = 15;
    public static final int TYPE_COURSE = 25;
    public static final int TYPE_LEPORTS = 28;
    public static final int TYPE_ACCOMMODATION = 32;
    public static final int TYPE_SHOPPING = 38;
    public static final int TYPE_EATERY = 39;
    public static final int TYPE_GREEN_TOUR = 2000;

    /**
     * 아래 해당하는 키에 대해서만 선택적으로 취한다.
     * 아래 키에 해당하지 않는 것에 대해서는 무시한다.
     * - 아래 키 이외의 필드는 `detailCommon`을 통해 받아들이도록 한다.
     */
    private static final List<String> GOOD_KEYS = Arrays.asList(
            "contentid",
            "contenttypeid",
            "modifiedtime",
            "readcount",
            "withtour",
            "greentour"
    );

    public static Master wrap(Map<String, Object> map) {
        Master master;

        Boolean isGreenTour = (Boolean) map.get("greentour");
        if (isGreenTour != null && isGreenTour) {
            master = new GreenMaster();
        } else {
            master = new Master();
        }

        for (String key : map.keySet()) {
            master.put(key, map.get(key));
        }
        return master;
    }

    @Override
    public Object put(String key, Object value) {
        String lowercaseKey = key.toLowerCase();
        if (!GOOD_KEYS.contains(lowercaseKey)) {
            return null;
        }
        return super.put(key, value);
    }

    public String getContentId() {
        return get("contentid").toString();
    }

    public int getContentTypeId() {
        // NOTE. `contenttypeid` 값이 없는 경우는 **생태관광**일 경우이다.
        return Integer.parseInt(get("contenttypeid").toString());
    }

    public int getReadCount() {
        return get("readcount") != null ? (int) get("readcount") : 0;
    }

    public long getModifiedDate() {
        return Long.parseLong(get("modifiedtime").toString());
    }

    public void setWithTour(boolean withTour) {
        put("withtour", withTour);
    }

    public boolean isWithTour() {
        return get("withtour") != null && (boolean) get("withtour");
    }

    public void setGreenTour(boolean greenTour) {
        put("greentour", greenTour);
    }

    public boolean isGreenTour() {
        return get("greentour") != null && (boolean) get("greentour");
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Master)) return false;
        if (this == o) return true;
        return getContentId().equals(((Master) o).getContentId());
    }
}
