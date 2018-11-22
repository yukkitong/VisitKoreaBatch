package kr.co.uniess.vk.batch;

import kr.co.uniess.vk.batch.component.TourApiClientCallableFactory;
import kr.co.uniess.vk.batch.component.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

@Component
public class FetchRunner implements Command<String> {

    private final static Logger logger = LoggerFactory.getLogger(FetchRunner.class);

    private final static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");


    // `result` 리스트에는 정해진 기간동안에 추가되거나 업데이트된 모든 마스터 정보가 포함되어있다.
    private List<Map<String, Object>> aggregationList = new ArrayList<>();


    private static long dateStart(String arg) {
        return date(arg + "000000");
    }

    private static long dateEnd(String arg) {
        return date(arg + "235959"); // 23hour 59 min. 59 sec.
    }

    private static long date(String string) {
        try {
            return Long.parseLong(string);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Map<String, Object>> getResultList() {
        return aggregationList;
    }

    @Override
    public void execute(String... args) {
        final long start = dateStart(args[0]), end = dateEnd(args[1]);

        logger.info("Period - start {}, end {}", start, end);

        if (start < 0 || end < 0) {
            logger.error("Invalid date error.");
            System.exit(-1);
        }

        if (start > end) {
            logger.error("Invalid date error.(start must be equal or less than end.)");
            System.exit(-1);
        }

        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        Future<List<Master>> listFuture1 = null; // threadPool.submit(TourApiClientCallableFactory.getKorServiceMasterCallable(start, end));
        Future<List<Master>> listFuture2 = null; // threadPool.submit(TourApiClientCallableFactory.getKorWithServiceMasterCallable(start, end));
        Future<List<Master>> listFuture3 = threadPool.submit(TourApiClientCallableFactory.getGreenTourServiceMasterCallable(start, end));

        ArrayList<Master> result = new ArrayList<>();
        try {
            if (listFuture1 != null) {
                List<Master> korTourList = listFuture1.get();
                // Aggregation all list
                result.addAll(korTourList);
            }

            if (listFuture2 != null) {
                List<Master> withTourList = listFuture2.get();
                // 국문 지역기반 관광 정보에는 무장애관광 정보가 포함되어있다.
                // 무장애관광 정보는 부서 정보가 따로 없어 로직으로 표시를 해둬서
                // 이후 DB 업데이트 과정에서 부서 처리를 하여야 한다.
                // 때문에 아래와 같이 국문관광 정보에서 무장애 관광정보와 동일한 건에 대해
                // 삭제 처리한다.
                for (Master item : withTourList) {
                    // 무장애관광 정보의 경우 태깅 처리하여
                    // 이후 DB 업데이트 처리할때 부서 처리를 할수 있도록 한다.
                    if (!result.remove(item)) {
                        item.setWithTour(true);
                    }
                }
                result.addAll(withTourList);
            }

            if (listFuture3 != null) {
                List<Master> greenTourList = listFuture3.get();
                // 생태관광 정보의 경우 태깅 처리하여
                // 이후 DB 업데이트 처리할때 부서 처리를 할수 있도록 한다.
                // 생태관광의 경우 Tour API로 부터 내려오는 데이터 포맷이 다르다.
                // - 마스터 정보만 존재하며(상세 반복 정보 등이 없음), `overview` 필드가 아닌 `summary` 필드가 이를 대신한다.
                // - 생태관광일 경우 Content Type ID 를 `2000` 으로 고정한다. @see GreenMaster
                for (Master item : greenTourList) {
                    item.setGreenTour(true);
                }
                // Aggregation all list
                result.addAll(greenTourList);
            }

            // Aggregation 일괄 처리
            for (Master master : result) {
                try {
                    Map<String, Object> item = new HashMap<>();
                    if (master.isGreenTour()) {
                        item.put("master", master);
                    } else {
                        final String contentId = master.getContentId();
                        final int contentTypeId = master.getContentTypeId();

                        Future<Map<String, Object>> futureCommon = threadPool.submit(TourApiClientCallableFactory.getKorServiceCommonCallable(contentId, contentTypeId));
                        Future<Map<String, Object>> futureIntro = threadPool.submit(TourApiClientCallableFactory.getKorServiceIntroCallable(contentId, contentTypeId));
                        Future<List<Map<String, Object>>> futureInfoList = threadPool.submit(TourApiClientCallableFactory.getKorServiceInfoCallable(contentId, contentTypeId));
                        Future<Map<String, Object>> futureWithTour = threadPool.submit(TourApiClientCallableFactory.getKorWithServiceDetailWithTourCallable(contentId, contentTypeId));
                        Future<List<Map<String, Object>>> futureImageList = threadPool.submit(TourApiClientCallableFactory.getKorServiceImageCallable(contentId));

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
                    }
                    aggregationList.add(item);
                } catch(Exception e) {
                    // ignore and skip..
                    logger.error("SKIPPED CID: " + master.getContentId(), e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
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
