package kr.co.uniess.vk.batch.component.model;

import java.util.HashMap;

/**
 * **Tour API** 를 이용할때 국문관광 서비스와 달리 `생태관광`은 Response Layout (포맷) 이 다르므로
 * 별도의 데이터 모델이 필요하게되었다.
 * - 인간만이 하는 사고능력, 사고능력 중에서도 일반화 추상화에 대한 고차원 능력을 포기한 API란 생각이 든다.
 * - 도무지 이해할래야 이해가 안된다.
 * - 한 조직에서도 부서간 협업이 안되고 경쟁의 대상으로만 생각한다니 참 ... 할 말이 없다.
 * - 조직의 수장의 문제일까? 대한민국 전반의 문화, 통념의 문제일까?
 */
public class GreenMaster extends Master {

    public String getTitle() {
        return get("title").toString();
    }

    /**
     * 생태관광일 경우 Tour API를 통해 전달받은 모든 필드를 저장한다.
     * @param key
     * @param value
     * @return
     */
    @Override
    public Object put(String key, Object value) {
        String lowercaseKey = key.toLowerCase();
        if (lowercaseKey.equals("mainimage")) {
            return delegateMap.put("firstimage", value);
        }
        // NOTE. `summary` - 생태관광의 경우에 `overview` 필드와 같은 성격의 필드 키이다.
        if (lowercaseKey.equals("summary")) {
            return delegateMap.put("overview", value);
        }
        return delegateMap.put(key, value);
    }

    /**
     * 생태관광일 경우 Content Type ID 를 `2000` 으로 고정한다.
     * @param isGreenTour
     */
    public void setGreenTour(boolean isGreenTour) {
        if (isGreenTour) {
            put("contenttypeid", TYPE_GREEN_TOUR);
        } else {
            remove("contenttypeid");
        }
        super.setGreenTour(isGreenTour);
    }
}
