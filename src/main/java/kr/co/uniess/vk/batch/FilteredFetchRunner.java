package kr.co.uniess.vk.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * NOTE. `detailCommon` API를 이용하여 해당 content id에 대해 재요청하려고 하였으나,
 * 해당 컨텐트에 대한 정보중 `무장애관광`, `생태관광` 인지 구분값이 없어
 * 결국에는 날짜별로 정렬해서 요청해야하는 기본 API(지역기반) 요청을 해야할 것 같다. (ㅜㅜ)
 */
@Component
public class FilteredFetchRunner implements Command<String> {

    private final static Logger logger = LoggerFactory.getLogger(FilteredFetchRunner.class);

    // `result` 리스트에는 정해진 기간동안에 추가되거나 업데이트된 모든 마스터 정보가 포함되어있다.
    private List<Map<String, Object>> aggregationList = new ArrayList<>();

    public List<Map<String, Object>> getResultList() {
        return aggregationList;
    }

    @Override
    public void execute(String... arg) {
        final String[] targetContentIds = arg;
    }
}
