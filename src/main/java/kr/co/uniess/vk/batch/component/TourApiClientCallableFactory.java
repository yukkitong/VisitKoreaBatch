package kr.co.uniess.vk.batch.component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.uniess.vk.batch.component.model.GreenMaster;
import kr.co.uniess.vk.batch.component.model.Master;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class TourApiClientCallableFactory {

    public static Callable<List<Master>> getKorServiceMasterCallable(long start, long end) {
        return createTourAPIMasterCallable(TourApiClient.builder().path("KorService/areaBasedList"), start, end);
    }

    public static Callable<List<Master>> getKorWithServiceMasterCallable(long start, long end) {
        return createTourAPIMasterCallable(TourApiClient.builder().path("KorWithService/areaBasedList"), start, end);
    }

    public static Callable<List<Master>> getGreenTourServiceMasterCallable(long start, long end) {
        return createTourAPIGreenMasterCallable(TourApiClient.builder().path("GreenTourService/areaBasedList"), start, end);
    }

    public static Callable<Map<String, Object>> getKorServiceCommonCallable(String contentId) {
        return createTourAPICommonCallable(TourApiClient.builder().contentId(contentId));
    }

    public static Callable<Map<String, Object>> getKorServiceCommonCallable(String contentId, int contentTypeId) {
        return createTourAPICommonCallable(TourApiClient.builder().contentId(contentId).contentTypeId(contentTypeId));
    }

    public static Callable<Map<String, Object>> getKorServiceIntroCallable(String contentId, int contentTypeId) {
        return createTourAPIIntroCallable(TourApiClient.builder().contentId(contentId).contentTypeId(contentTypeId));
    }

    public static Callable<List<Map<String, Object>>> getKorServiceInfoCallable(String contentId, int contentTypeId) {
        return createTourAPIInfoCallable(TourApiClient.builder().contentId(contentId).contentTypeId(contentTypeId));
    }

    public static Callable<List<Map<String, Object>>> getKorServiceImageCallable(String contentId) {
        return createTourAPIImageCallable(TourApiClient.builder().contentId(contentId));
    }

    public static Callable<Map<String, Object>> getKorWithServiceDetailWithTourCallable(String contentId, int contentTypeId) {
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

            boolean tracking = false;
            boolean needToFetchMore = true;
            while (needToFetchMore) {
                TourApiClient client = builder.build();
                JsonNode root = mapper.readTree(client.getMasterURL());
                JsonNode item = root.findPath("item");
                if (item.isArray()) {
                    String itemString = item.toString();
                    List<Master> items = mapper.readValue(itemString, typeReference);
                    for (Master i : items) {
                        if (i.getModifiedDate() <= end) {
                            tracking = true;
                        }

                        if (tracking) {
                            if (i.getModifiedDate() >= start) {
                                needToFetchMore = false;
                            }
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

    /**
     * 각각의 Tour API 요청 Callable 생성 Factory Method - 생태관광 전용
     * @param builder URL Builder
     * @param start   start date
     * @param end     end date
     * @return List
     */
    private static Callable<List<Master>> createTourAPIGreenMasterCallable(TourApiClient.TourApiClientBuilder builder, long start, long end) {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<GreenMaster>> typeReference = new TypeReference<List<GreenMaster>>() {};
        return () -> {
            List<Master> resultList = new ArrayList<>();

            boolean tracking = false;
            boolean needToFetchMore = true;
            while (needToFetchMore) {
                TourApiClient client = builder.build();
                JsonNode root = mapper.readTree(client.getMasterURL());
                JsonNode item = root.findPath("item");
                if (item.isArray()) {
                    String itemString = item.toString();
                    List<Master> items = mapper.readValue(itemString, typeReference);
                    for (Master i : items) {
                        if (i.getModifiedDate() <= end) {
                            tracking = true;
                        }

                        if (tracking) {
                            if (i.getModifiedDate() >= start) {
                                needToFetchMore = false;
                            }
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

    private static Callable<List<Map<String, Object>>> createTourAPIImageCallable(TourApiClient.TourApiClientBuilder builder) {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Map<String, Object>>> typeReference = new TypeReference<List<Map<String, Object>>>() {};
        return () -> {
            List<Map<String, Object>> resultList = new ArrayList<>();
            while (true) {
                TourApiClient client = builder.build();
                JsonNode root = mapper.readTree(client.getDetailImageURL());
                JsonNode item = root.findPath("item");
                if (item.isArray()) {
                    String itemString = item.toString();
                    List<Map<String, Object>> items = mapper.readValue(itemString, typeReference);
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

    private static Callable<Map<String, Object>> createTourAPIWithTourCallable(TourApiClient.TourApiClientBuilder builder) {
        ObjectMapper mapper = new ObjectMapper();
        return () -> {
            TourApiClient client = builder.build();
            JsonNode root = mapper.readTree(client.getDetailWithTourURL());
            JsonNode item = root.findPath("item");
            if (item.isObject()) {
                String itemString = root.findPath("item").toString();
                return mapper.readValue(itemString, new TypeReference<Map<String, Object>>() {});
            }
            return null;
        };
    }
}
