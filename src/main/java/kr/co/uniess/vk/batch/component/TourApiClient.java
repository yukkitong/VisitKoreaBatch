package kr.co.uniess.vk.batch.component;

import lombok.Builder;
import lombok.Getter;

import java.net.MalformedURLException;
import java.net.URL;


// TODO Refactor to Abstract Factory Pattern, because there are various APIs.

@Builder
public class TourApiClient {

    /**
     * Tour API 요청에 필요한 기본값
     */
    private static final String BASE_URL = "http://api.visitkorea.or.kr/openapi/service/rest";
    private static final String SERVICE_KEY = "A%2BycgFhk2eYE6mEw%2B6%2FhcCbRDaCPGJf3aLCdYyfzuqRx6iY2b%2F04BmXgnQoTrGhm1FBQ%2BOVA5mbMogKlHFcDgw%3D%3D";
    private static final String MOBILE_OS = "ETC";
    private static final String MOBILE_APP = "BatchApp";

    /**
     * 서비스 종류를 명시하여 필요한 서비스를 요청 가능하도록 한다.
     */
    private String path;

    /**
     * 컨텐트 ID - 상세 정보 요청시 필요
     */
    private String contentId;

    /**
     * 컨텐트 타입 - 상세 정보 요청시 필요
     */
    private int contentTypeId;

    /**
     * 페이지 번호
     * 기본값 1 (Not zero base)
     */
    @Getter
    @Builder.Default
    private int pageNo = 1;

    /**
     * 한번에 요청가능한 리스트 수
     * 기본값 100
     */
    @Getter
    @Builder.Default
    private int rowsPerPage = 100;

    /**
     * 지역기반 API 요청을 위한 URL을 생성 반환한다.
     * 여기서 수정날짜 기준의 정렬값인 `arrange=C` 쿼리 파라미터를 주요하게 봐야한다.
     * API요청 결과리스트가 수정날짜 기준으로 내림차순 정렬되어 반환된다.
     * 이는 날짜범위로 요청하는 API가 없기 때문(ㅡㅡ;)에 날짜로 정렬시켜 범위로 필터링할 수도록 하기 위함이다.
     * @return URL
     */
    public URL getMasterURL() {
        try {
            return new URL(BASE_URL + (path.startsWith("/") ? "" : "/") + path +
                    "?ServiceKey=" + SERVICE_KEY +
                    "&MobileOS=" + MOBILE_OS +
                    "&MobileApp=" + MOBILE_APP +
                    "&pageNo=" + pageNo +
                    "&numOfRows=" + rowsPerPage +
                    "&arrange=C&_type=json");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public URL getDetailCommonURL() {
        try {
            return new URL(BASE_URL + "/KorService/detailCommon" +
                    "?ServiceKey=" + SERVICE_KEY +
                    "&MobileOS=" + MOBILE_OS +
                    "&MobileApp=" + MOBILE_APP +
                    "&contentId=" + contentId +
                    ((contentTypeId != 0) ? ("&contentTypeId=" + contentTypeId) : "") +
                    "&defaultYN=Y" +
                    "&firstImageYN=Y" +
                    "&areacodeYN=Y" +
                    "&catcodeYN=Y" +
                    "&addrinfoYN=Y" +
                    "&mapinfoYN=Y" +
                    "&overviewYN=Y" +
                    "&_type=json");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public URL getDetailCommonOverviewURL() {
        try {
            return new URL(BASE_URL + "/KorService/detailCommon" +
                    "?ServiceKey=" + SERVICE_KEY +
                    "&MobileOS=" + MOBILE_OS +
                    "&MobileApp=" + MOBILE_APP +
                    "&contentId=" + contentId +
                    "&contentTypeId=" + contentTypeId +
                    "&overviewYN=Y" +
                    "&_type=json");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public URL getDetailIntroURL() {
        try {
            return new URL(BASE_URL + "/KorService/detailIntro" +
                    "?ServiceKey=" + SERVICE_KEY +
                    "&MobileOS=" + MOBILE_OS +
                    "&MobileApp=" + MOBILE_APP +
                    "&contentId=" + contentId +
                    "&contentTypeId=" + contentTypeId +
                    "&introYN=Y" +
                    "&_type=json");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public URL getDetailInfoURL() {
        try {
            return new URL(BASE_URL + "/KorService/detailInfo" +
                    "?ServiceKey=" + SERVICE_KEY +
                    "&MobileOS=" + MOBILE_OS +
                    "&MobileApp=" + MOBILE_APP +
                    "&contentId=" + contentId +
                    "&contentTypeId=" + contentTypeId +
                    "&_type=json");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public URL getDetailImageURL() {
        try {
            return new URL(BASE_URL + "/KorService/detailImage" +
                    "?ServiceKey=" + SERVICE_KEY +
                    "&MobileOS=" + MOBILE_OS +
                    "&MobileApp=" + MOBILE_APP +
                    "&contentId=" + contentId +
                    "&imageYN=Y" +
                    "&subImageYN=Y" +
                    "&_type=json");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public URL getDetailWithTourURL() {
        try {
            return new URL(BASE_URL + "/KorWithService/detailWithTour" +
                    "?ServiceKey=" + SERVICE_KEY +
                    "&MobileOS=" + MOBILE_OS +
                    "&MobileApp=" + MOBILE_APP +
                    "&contentId=" + contentId +
                    "&contentTypeId=" + contentTypeId +
                    "&_type=json");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
