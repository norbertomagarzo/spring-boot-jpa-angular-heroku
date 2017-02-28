package pgoggin.job;

import pgoggin.models.Quote;
import pgoggin.models.Stock;
import pgoggin.models.StockDao;
import pgoggin.models.StockDaoDbImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.sun.deploy.config.JREInfo.getAll;

/**
 * Created by patrickgoggin on 2/27/17.
 */

@Component
public class RecordQuotes{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //@Autowired
    //private StockDaoDbImpl dao;

    @Autowired
    private StockDao dao;
//"*/30 * * * * *"

  /*EVERY 30 MINUTES BETWEEN 8:00 AM AND 3:00 PM, MONDAY THROUGH FRIDAY */ // "0 0/30 8-15 * * MON-FRI"
    @Scheduled(
            cron = "0 0/30 8-15 * * MON-FRI")
    public void cronJob(){
        List<Stock> l = dao.getAll();
        String s = "";
        for(Stock st : l){
            Quote q = dao.newQuote(st.getT());
            s = s + st.getName();
            dao.createQuote(q);
        }
        //String s = dao.getAll().
        System.out.println("Job at "+ new Date());
        System.out.println("Portfolio: "+ s);
    }

}
