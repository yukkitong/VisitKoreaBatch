package kr.co.uniess.vk.batch;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootApplication
@MapperScan(basePackages = "kr.co.uniess.vk.batch.repository")
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    private static final String VERSION = "version";
    private static final String HELP = "help";

    private static final String VERSION_CODE = "1.0-SNAPSHOT";

    private static final String DEFAULT_OUTPUT_JSON_FILENAME = "tour-api-result.json";

    @Autowired
    @Lazy
    private DataManipulateRunner dataManipulateRunner;

    @Autowired
    @Lazy
    private FilteredFetchRunner filteredFetchRunner;

    @Autowired
    @Lazy
    private FetchRunner fetchRunner;

    @Bean
    @Profile("!test")
    public ApplicationRunner applicationRunner(ApplicationContext context) {
        return (ApplicationArguments args) -> {

            if (args.getSourceArgs().length == 0) {
                printVersion();
                printHelp();
                return;
            } else if (args.containsOption(VERSION)) {
                printVersion();
                return;
            } else if (args.containsOption(HELP)) {
                printHelp();
                return;
            }

            if (args.getSourceArgs()[0].equals("fetch")) {                       // fetch only
                if (args.containsOption("dates")) {
                    String dates = args.getOptionValues("dates").get(0);
                    if (dates.contains(",")) {
                        String[] startEnd = dates.split(",");
                        fetchRunner.execute(startEnd[0], startEnd[1]);
                    } else {
                        fetchRunner.execute(dates, dates);
                    }
                    writeToFile(fetchRunner.getResultList(), args);
                } else if (args.containsOption("cid-file")) {
                    String cidFileName = args.getOptionValues("cid-file").get(0);
                    filteredFetchRunner.execute(loadContentIdArray(cidFileName));
                    writeToFile(filteredFetchRunner.getResultList(), args);
                } else if (args.containsOption("cid")) {
                    filteredFetchRunner.execute(args.getOptionValues("cid").get(0).split(","));
                    writeToFile(filteredFetchRunner.getResultList(), args);
                } else {
                    printHelp();
                }
            } else if (args.getSourceArgs()[0].equals("update")) {
                if (args.containsOption("dates")) {                        // fetch and update
                    String dates = args.getOptionValues("dates").get(0);
                    if (dates.contains(",")) {
                        String[] startEnd = dates.split(",");
                        fetchRunner.execute(startEnd[0], startEnd[1]);
                    } else {
                        fetchRunner.execute(dates, dates);
                    }
                    writeToFile(fetchRunner.getResultList(), args);
                    dataManipulateRunner.execute(fetchRunner.getResultList());
                } else if (args.containsOption("file")) {                  // update only with a file
                    List<Map<String, Object>> resultList = null;
                    try {
                        final String filename = args.getOptionValues("file").get(0);
                        resultList = loadResultList(filename);
                    } catch (JsonParseException e) {
                        System.err.println("잘못된 형식의 파일입니다.");
                        logger.error(e.getMessage(), e);
                        System.exit(-1);
                    } catch (IOException e) {
                        System.err.println("파일 로딩중 에러가 발생하여였습니다.");
                        logger.error(e.getMessage(), e);
                        System.exit(-1);
                    }
                    dataManipulateRunner.execute(resultList);
                } else if (args.containsOption("cid-file")) {
                    String cidFileName = args.getOptionValues("cid-file").get(0);
                    filteredFetchRunner.execute(loadContentIdArray(cidFileName));
                    writeToFile(filteredFetchRunner.getResultList(), args);
                    dataManipulateRunner.execute(filteredFetchRunner.getResultList());
                } else if (args.containsOption("cid")) {
                    filteredFetchRunner.execute(args.getOptionValues("cid").get(0).split(","));
                    writeToFile(filteredFetchRunner.getResultList(), args);
                    dataManipulateRunner.execute(filteredFetchRunner.getResultList());
                } else {
                    printHelp();
                }
            } else {
                printHelp();
            }
        };
    }

    private String[] loadContentIdArray(String fileName) throws FileNotFoundException {
        ArrayList<String> result = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.add(line);
            }
        }
        return result.toArray(new String[0]);
    }

    private List<Map<String, Object>> loadResultList(String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(fileName), new TypeReference<List<Map<String, Object>>>() {});
    }

    private String getOutputFolderName() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return "TOUR_API_" + format.format(new Date());
    }

    private void writeToFile(List<Map<String, Object>> list, ApplicationArguments args) {
        try {
            if (args.containsOption("output")) {
                String fileName = args.getOptionValues("output").get(0);
                writeToFileInternal(list, fileName);
            } else {
                writeToFileInternal(list, DEFAULT_OUTPUT_JSON_FILENAME);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToFileInternal(List<Map<String, Object>> list, String fileName) throws IOException {
//        File folder = new File(getOutputFolderName());
//        if (!folder.exists()) {
//            if (!folder.mkdir()) {
//                throw new IOException("Couldn't create new directory.");
//            }
//        }

//        File file = new File(folder, fileName);
        File file = new File(fileName);
        ObjectWriter writer = new ObjectMapper().writer();
        writer.writeValue(file, list);
    }

    private void printVersion() {
        System.out.println("Version: " + VERSION_CODE);
    }

    private void printHelp() {
        System.out.println("VisitKoreaBatch " + VERSION_CODE);
        System.out.println("Usage:");
        System.out.println("      java -jar VisitKoreaBatch-[Version Name].jar [command] --[option]=[value] ...");
        System.out.println("      command:");
        System.out.println("          - fetch: Tour API 요청하여 데이터를 수집 저장합니다.");
        System.out.println("          - update: Tour API를 통해 수집 및 수집된 데이터를 기반으로 DB와 동기화 작업을 수행합니다.");
        System.out.println("      fetch option:");
        System.out.println("                - dates=[start-date],[end-date]: 지정한 기간동안의 변경 정보를 수집합니다.");
        System.out.println("                - dates=[date]: 특정날짜(하루) 동안의 변경 정보를 수집합니다.");
        System.out.println("                - cid-file=[file-name]: cid 목록파일을 제공하여 지정된 cid에 대한 정보만 선택적으로 수집합니다.");
        System.out.println("                - cid=[cid1],[cid2],..,[cidn]: cid 텍스트 목록을 제공하여 지정된 cid에 대한 정보만 선택적으로 수집합니다.");
        System.out.println("                - output=[json file name]: Tour API를 통해 수집한 정보를 JSON 파일로 저장할 파일명을 지정합니다.");
        System.out.println("      update option:");
        System.out.println("                - file=[json file path]: 이미 수집된 파일(JSON)을 기반으로 DB 동기화 작업을 수행합니다.");
        System.out.println("                - cid-file=[file-name]: cid 목록파일을 제공하여 지정된 cid에 대한 정보만 선택적으로 수집합니다.");
        System.out.println("                - cid=[cid1],[cid2],..,[cidn]: cid 텍스트 목록을 제공하여 지정된 cid에 대한 정보만 선택적으로 수집합니다.");
        System.out.println("                - output=[json file name]: Tour API를 통해 수집한 정보를 JSON 파일로 저장할 파일명을 지정합니다.");
    }
}
