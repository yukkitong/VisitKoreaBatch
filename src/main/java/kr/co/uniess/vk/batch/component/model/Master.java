package kr.co.uniess.vk.batch.component.model;

import java.util.HashMap;
import java.util.Map;

public class Master extends HashMap<String, Object> {

    public static Master wrap(Map<String, Object> map) {
        Master master = new Master();
        for (String key : map.keySet()) {
            master.put(key, map.get(key));
        }
        return master;
    }

    @Override
    public Object put(String key, Object value) {
        String lowercaseKey = key.toLowerCase();
        if (!lowercaseKey.equals("contentid")
                && !lowercaseKey.equals("contenttypeid")
                && !lowercaseKey.equals("createdtime")
                && !lowercaseKey.equals("modifiedtime")
                && !lowercaseKey.equals("readcount")
                && !lowercaseKey.equals("withtour")
                && !lowercaseKey.equals("greentour")) {
            return null;
        }
        return super.put(key, value);
    }

    public String getContentId() {
        return get("contentid").toString();
    }

    public int getContentTypeId() {
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
