package kr.co.uniess.vk.batch;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class SomethingToTest {

//    @Test
//    public void testGetTodayDate() {
//        final Date today = new Date();
//        DateFormat format = new SimpleDateFormat("yyyyMMdd");
//        String formattedToday = format.format(today);
//        Assert.assertEquals("20181029", formattedToday);
//    }
//
//    @Test
//    public void testGetYesterdayDate() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DATE, -1);
//        final Date yesterday = calendar.getTime();
//        DateFormat format = new SimpleDateFormat("yyyyMMdd");
//        String formattedYesterday = format.format(yesterday);
//        Assert.assertEquals("20181028", formattedYesterday);
//    }

    @Test
    public void testPageCountFormula() {
        final int totalItemRows = 12345;
        final int rowsPerPage = 30;
        Assert.assertEquals(412, (int) Math.ceil((double) totalItemRows / rowsPerPage));
    }

    @Test
    public void testCreateFile() {
        String path = "/home/jason/workspace-3/VistitKoreaBatch/TOUR_API_20181031/tour-api-result.json";
        File file = new File(path);
        try {
            boolean created = file.createNewFile();
            Assert.assertTrue(created);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
