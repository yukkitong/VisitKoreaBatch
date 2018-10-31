package kr.co.uniess.vk.batch;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import kr.co.uniess.vk.batch.component.TourApiClient;
import kr.co.uniess.vk.batch.model.*;
import kr.co.uniess.vk.batch.controller.CommandArgsParser;
import kr.co.uniess.vk.batch.controller.KTOController;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

@Component
@MapperScan(basePackages = "kr.co.uniess.vk.batch.kr.co.uniess.vk.batch.repository")
public class CommandLineExecutor implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(CommandLineExecutor.class);

    @Autowired
    private CommandArgsParser commandArgsParser;

    @Autowired
    private KTOController controller;

    private final static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

    private static long start() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -5);
        return Long.parseLong(format.format(calendar.getTime()) + "000000");
    }

    // TODAY
    private static long end() {
        return Long.parseLong(format.format(new Date()) + "000000");
    }

    @Override
    public void run(String... args) {

        // TODO parse command line arguments
        commandArgsParser.parse(args);

        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        // TODO: `start`, `end`는 테스트 완료후 command line argument 로 처리하여야 한다.
        final long start = start(), end = end();

        logger.info("START {}", start);
        logger.info("END   {}", end);

        Future<List<Master>> listFuture1 = threadPool.submit(getKorServiceMasterCallable(start, end));
        Future<List<Master>> listFuture2 = threadPool.submit(getKorWithServiceMasterCallable(start, end));
        Future<List<Master>> listFuture3 = threadPool.submit(getGreenTourServiceMasterCallable(start, end));

        ArrayList<Master> result = new ArrayList<>();
        try {
            List<Master> korTourList = listFuture1.get();
            List<Master> withTourList = listFuture2.get();
            List<Master> greenTourList = listFuture3.get();

            // 국문 지역기반 관광 정보에는 무장애관광 정보가 포함되어있다.
            // 무장애관광 정보는 부서 정보가 따로 없어 로직으로 표시를 해둬서
            // 이후 DB 업데이트 과정에서 부서 처리를 하여야 한다.
            // 때문에 아래와 같이 국문관광 정보에서 무장애 관광정보와 동일한 건에 대해
            // 삭제 처리한다.
            for (Master item : withTourList) {
                korTourList.remove(item);
            }

            // 무장애관광 정보의 경우 태깅 처리하여
            // 이후 DB 업데이트 처리할때 부서 처리를 할수 있도록 한다.
            for (Master item : withTourList) {
                item.setWithTour(true);
            }

            // 생태관광 정보의 경우 태깅 처리하여
            // 이후 DB 업데이트 처리할때 부서 처리를 할수 있도록 한다.
            for (Master item : greenTourList) {
                item.setGreenTour(true);
            }

            // Aggregation all list
            result.addAll(korTourList);
            result.addAll(withTourList);
            result.addAll(greenTourList);

            // `result` 리스트에는 정해진 기간동안에 추가되거나 업데이트된 모든 마스터 정보가 포함되어있다.
            HashMap<Master, HashMap<String, Object>> aggregationMap = new HashMap<>();
            for (Master master : result) {
                final String contentId = master.getContentId();
                final int contentTypeId = master.getContentTypeId();

                Future<Map<String, Object>> futureCommon = threadPool.submit(getKorServiceCommonCallable(contentId, contentTypeId));
                Future<Map<String, Object>> futureIntro = threadPool.submit(getKorServiceIntroCallable(contentId, contentTypeId));
                Future<List<Map<String, Object>>> futureInfoList = threadPool.submit(getKorServiceInfoCallable(contentId, contentTypeId));
                Future<DetailWithTour> futureWithTour = threadPool.submit(getKorWithServiceDetailWithTourCallable(contentId, contentTypeId));
                Future<List<Image>> futureImageList = threadPool.submit(getKorServiceImageCallable(contentId));

                HashMap<String, Object> item = new HashMap<>();
                item.put("master", master);
                if (futureCommon.get() != null) {
                    item.put("common", futureCommon.get());
                }
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

                aggregationMap.put(master, item);
            }

            // `aggregationMap` 에는 정해진 기간동안에 추가되거나 업데이트된 모든 정보가 포함되어있다.

            // Write json file
            ObjectWriter writer = new ObjectMapper().writerWithDefaultPrettyPrinter();
            // FIXME : Exception
            // File file = new File(getOutputFolderName() + "/tour-api-result.json");
            // writer.writeValue(file, aggregationMap.values());

            logger.info(writer.writeValueAsString(aggregationMap.values()));

            // Do process
            controller.process(aggregationMap);

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
            threadPool.shutdownNow();
        }
    }

    private String getOutputFolderName() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return "TOUR_API_" + format.format(new Date());
    }

    private static Callable<List<Master>> getKorServiceMasterCallable(long start, long end) {
        return createTourAPIMasterCallable(TourApiClient.builder().path("KorService/areaBasedList"), start, end);
    }

    private static Callable<List<Master>> getKorWithServiceMasterCallable(long start, long end) {
        return createTourAPIMasterCallable(TourApiClient.builder().path("KorWithService/areaBasedList"), start, end);
    }

    private static Callable<List<Master>> getGreenTourServiceMasterCallable(long start, long end) {
        return createTourAPIMasterCallable(TourApiClient.builder().path("GreenTourService/areaBasedList"), start, end);
    }

    private static Callable<Map<String, Object>> getKorServiceCommonCallable(String contentId, int contentTypeId) {
        return createTourAPICommonCallable(TourApiClient.builder().contentId(contentId).contentTypeId(contentTypeId));
    }

    private static Callable<Map<String, Object>> getKorServiceIntroCallable(String contentId, int contentTypeId) {
        return createTourAPIIntroCallable(TourApiClient.builder().contentId(contentId).contentTypeId(contentTypeId));
    }

    private static Callable<List<Map<String, Object>>> getKorServiceInfoCallable(String contentId, int contentTypeId) {
        return createTourAPIInfoCallable(TourApiClient.builder().contentId(contentId).contentTypeId(contentTypeId));
    }

    private static Callable<List<Image>> getKorServiceImageCallable(String contentId) {
        return createTourAPIImageCallable(TourApiClient.builder().contentId(contentId));
    }

    private static Callable<DetailWithTour> getKorWithServiceDetailWithTourCallable(String contentId, int contentTypeId) {
        return createTourAPIWithTourCallable(TourApiClient.builder().contentId(contentId).contentTypeId(contentTypeId));
    }

    /**
     * 각각의 Tour API 요청 Callable 생성 Factory Method
     * @param builder URL Builder
     * @param start   start date
     * @param end     end date
     * @return List
     */
    private static Callable<List<Master>> createTourAPIMasterCallable(TourApiClient.TourApiClientBuilder builder, long start, long end) {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Master>> typeReference = new TypeReference<List<Master>>() {};
        return () -> {
            List<Master> resultList = new ArrayList<>();

            boolean needToFetchMore = true;
            while (needToFetchMore) {
                TourApiClient client = builder.build();
                JsonNode root = mapper.readTree(client.getMasterURL());
                JsonNode item = root.findPath("item");
                if (item.isArray()) {
                    String itemString = item.toString();
                    List<Master> items = mapper.readValue(itemString, typeReference);
                    for (Master i : items) {
                        if (i.getModifiedDate() >= start && i.getModifiedDate() < end) {
                            resultList.add(i);
                        } else {
                            needToFetchMore = false;
                        }
                    }
                }

                final int totalCount = root.findPath("totalCount").asInt();
                if ((int) Math.ceil((double) totalCount / client.getRowsPerPage()) < client.getPageNo()) {
                    break;
                }

                if (needToFetchMore) {
                    builder.pageNo(client.getPageNo() + 1); // increase page!!
                }
            }
            return resultList;
        };
    }

    private static Callable<Map<String, Object>> createTourAPICommonCallable(TourApiClient.TourApiClientBuilder builder) {
        ObjectMapper mapper = new ObjectMapper();
        return () -> {
            TourApiClient client = builder.build();
            JsonNode root = mapper.readTree(client.getDetailCommonURL());
            JsonNode item = root.findPath("item");
            if (item.isObject()) {
                return mapper.readValue(item.toString(), new TypeReference<Map<String, Object>>() {});
            }
            return null;
        };
    }

    private static Callable<Map<String, Object>> createTourAPIIntroCallable(TourApiClient.TourApiClientBuilder builder) {
        ObjectMapper mapper = new ObjectMapper();
        return () -> {
            TourApiClient client = builder.build();
            JsonNode root = mapper.readTree(client.getDetailIntroURL());
            JsonNode item = root.findPath("item");
            if (item.isObject()) {
                return mapper.readValue(item.toString(), new TypeReference<Map<String, Object>>() {});
            }
            return null;
        };
    }

    private static Callable<List<Map<String, Object>>> createTourAPIInfoCallable(TourApiClient.TourApiClientBuilder builder) {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Map<String, Object>>> typeReference = new TypeReference<List<Map<String, Object>>>() {};
        return () -> {
            List<Map<String, Object>> resultList = new ArrayList<>();
            while (true) {
                TourApiClient client = builder.build();
                JsonNode root = mapper.readTree(client.getDetailInfoURL());
                JsonNode item = root.findPath("item");
                if (item.isArray()) {
                    List<Map<String, Object>> items = mapper.readValue(item.toString(), typeReference);
                    resultList.addAll(items);
                }

                final int totalCount = root.findPath("totalCount").asInt();
                if (totalCount == resultList.size() ||
                        (int) Math.ceil((double) totalCount / client.getRowsPerPage()) < (client.getPageNo() + 1)) {
                    break;
                }
                builder.pageNo(client.getPageNo() + 1); // increase page!!
            }
            return resultList;
        };
    }

    private static Callable<List<Image>> createTourAPIImageCallable(TourApiClient.TourApiClientBuilder builder) {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Image>> typeReference = new TypeReference<List<Image>>() {};
        return () -> {
            List<Image> resultList = new ArrayList<>();
            while (true) {
                TourApiClient client = builder.build();
                JsonNode root = mapper.readTree(client.getDetailImageURL());
                JsonNode item = root.findPath("item");
                if (item.isArray()) {
                    String itemString = item.toString();
                    List<Image> items = mapper.readValue(itemString, typeReference);
                    resultList.addAll(items);
                }

                final int totalCount = root.findPath("totalCount").asInt();
                if (totalCount == resultList.size() ||
                        (int) Math.ceil((double) totalCount / client.getRowsPerPage()) <(client.getPageNo() + 1)) {
                    break;
                }
                builder.pageNo(client.getPageNo() + 1); // increase page!!
            }
            return resultList;
        };
    }

    private static Callable<DetailWithTour> createTourAPIWithTourCallable(TourApiClient.TourApiClientBuilder builder) {
        ObjectMapper mapper = new ObjectMapper();
        return () -> {
            TourApiClient client = builder.build();
            JsonNode root = mapper.readTree(client.getDetailWithTourURL());
            JsonNode item = root.findPath("item");
            if (item.isObject()) {
                String itemString = root.findPath("item").toString();
                return mapper.readValue(itemString, DetailWithTour.class);
            }
            return null;
        };
    }
}
