package kr.co.uniess.vk.batch;

import kr.co.uniess.vk.batch.component.TourApiClientCallableFactory;
import kr.co.uniess.vk.batch.component.model.Master;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

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
    public void execute(String... targetContentIds) {
        if (targetContentIds == null || targetContentIds.length == 0) {
            return;
        }

        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        for (String contentId : targetContentIds) {
            Future<Map<String, Object>> futureCommon = threadPool.submit(TourApiClientCallableFactory.getKorServiceCommonCallable(contentId));
            try {
                Master master = Master.wrap(futureCommon.get());
                int contentTypeId = master.getContentTypeId();

                Future<Map<String, Object>> futureIntro = threadPool.submit(TourApiClientCallableFactory.getKorServiceIntroCallable(contentId, contentTypeId));
                Future<List<Map<String, Object>>> futureInfoList = threadPool.submit(TourApiClientCallableFactory.getKorServiceInfoCallable(contentId, contentTypeId));
                Future<Map<String, Object>> futureWithTour = threadPool.submit(TourApiClientCallableFactory.getKorWithServiceDetailWithTourCallable(contentId, contentTypeId));
                Future<List<Map<String, Object>>> futureImageList = threadPool.submit(TourApiClientCallableFactory.getKorServiceImageCallable(contentId));

                Map<String, Object> item = new HashMap<>();
                item.put("master", master);
                item.put("common", futureCommon.get());
                if (futureIntro.get() != null) {
                    item.put("intro", futureIntro.get());
                }
                if (futureInfoList.get() != null && !futureInfoList.get().isEmpty()) {
                    item.put("info", futureInfoList.get());
                }
                if (futureWithTour.get() != null) {
                    item.put("withtour", futureWithTour.get());
                }
                if (futureImageList.get() != null && !futureImageList.get().isEmpty()) {
                    item.put("image", futureImageList.get());
                }
                aggregationList.add(item);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(500, TimeUnit.MILLISECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
            threadPool.shutdownNow();
        }
    }
}
