package kr.co.uniess.vk.batch.component.model;

public class Master extends ApiData {

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

    public long getModifiedDate() {
        return Long.parseLong(get("modifiedtime").toString());
    }

    public void setWithTour(boolean withTour) {
        put("withtour", withTour);
    }

    public boolean isWithTour() {
        return (boolean) get("withtour");
    }

    public void setGreenTour(boolean greenTour) {
        put("greentour", greenTour);
    }

    public boolean isGreenTour() {
        return (boolean) get("greentour");
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Master)) return false;
        if (this == o) return true;
        return getContentId().equals(((Master) o).getContentId());
    }
}
