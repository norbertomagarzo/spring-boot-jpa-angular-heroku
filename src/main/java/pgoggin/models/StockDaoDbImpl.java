package pgoggin.models;

import com.google.gson.Gson;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import javax.persistence.PersistenceContext;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

/**
 * Created by patrickgoggin on 2/27/17.
 */

@Repository
@Transactional
@Component
public class StockDaoDbImpl implements StockDao{
    @PersistenceContext
    private EntityManager entityManager;

    private final RestTemplate restTemplate = new RestTemplate();
    //private final String uri = "https://api-snacks.nerderylabs.com/v1/snacks/?ApiKey=22076678-c4ff-439e-bfce-18e9a95dd48f";
    private final String uri = "https://www.google.com/finance/info?infotype=infoquoteall&q=C,AIG";
    private ArrayList<Stock> stockList;
    private HashMap<String, Stock> portfolio;
    public StockDaoDbImpl() {
        this.portfolio = new HashMap<String, Stock>();
    }
    //private Investor investor;
    private HashMap<String, HashMap<String, Stock>> portfolioHistory = new HashMap<String, HashMap<String, Stock>>();
    private double funds = 0;
    @Override
    public double getFunds(){


        return funds;
    }
    @Override
    public double addFunds(double amount){
        funds = funds + amount;
        return getFunds();
    }

    @Override
    public Transaction buyShares(Transaction t){
        double cost = newQuote(t.getSymbol()).getL() * t.getShareCount();
        Stock stock = getByTicker(t.getSymbol());
        int newShareCount = 0;
        if(funds >= cost){
            t.setFunds(funds - cost);
            funds = funds - cost;
            newShareCount = t.getShareCount() + stock.getMyShares();
            stock.setMyShares(newShareCount);
            t.setShareCount(newShareCount);
            update(stock);
            portfolio.put(stock.getT(), stock);
        }
        return t;
    }

    @Override
    public Transaction sellShares(Transaction t){
        double cost = newQuote(t.getSymbol()).getL() * t.getShareCount();
        Stock stock = getByTicker(t.getSymbol());
        int newShareCount = 0;
        if(funds >= cost){
            t.setFunds(funds + cost);
            funds = funds + cost;
            newShareCount = stock.getMyShares() - t.getShareCount();
            stock.setMyShares(newShareCount);
            t.setShareCount(newShareCount);
            update(stock);
            portfolio.put(stock.getT(), stock);

        }
        return t;
    }


    @Override
    public List<Stock> accessAPI() {
        String toReturn;
        ResponseEntity<String> apiResponse = restTemplate.exchange(uri,
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
                });
        toReturn = apiResponse.getBody();
        String jsonStr = ("[" + toReturn);
        Gson gson = new Gson();
        Stock stocks[] = gson.fromJson(jsonStr, Stock[].class);
        ArrayList<Stock> stockList = new ArrayList<Stock>(Arrays.asList(stocks));
        return stockList;
    }



    @Override
    public Stock getStockByID(int stockID) {
        return entityManager.find(Stock.class, stockID);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Stock> getAll() {
        return entityManager.createQuery("from Stock").getResultList();
    }
    @Override
    public HashMap<String, Stock> getPortfolioMap(){
    List<Stock> list = entityManager.createQuery("from Stock").getResultList();
        portfolio.clear();
        for(Stock s : list){
            portfolio.put(s.getT(), s);
        }
        return portfolio;
    }

    @Override
    public void recordQuotes(){
        List<Stock> l = getAll();
        HashMap<String, Stock> map = new HashMap<>();
        for(Stock s : l){
            map.put(s.getT(), s);
        }
        ArrayList<Quote> q = new ArrayList<>();
        for(Stock s : map.values()){
            Quote aQuote = newQuote(s.getT());
            map.get(aQuote.getT()).setL(aQuote.getL());
            update(map.get(aQuote.getT()));
            createQuote(aQuote);
        }

    }

    @Override
    public void create(Stock stock) {
        entityManager.persist(stock);
        return;
    }
    @Override
    public Quote newQuote(String t){
        String base = "https://www.google.com/finance/info?infotype=infoquoteall&q=";
        String toReturn;
        String thisUri = (base + t);
        ResponseEntity<String> apiResponse = restTemplate.exchange(thisUri,
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
                });
        toReturn = apiResponse.getBody();
        String jsonStr = ("[" + toReturn);
        Gson gson = new Gson();
        Stock stocks[] = gson.fromJson(jsonStr, Stock[].class);
        ArrayList<Stock> stockList = new ArrayList<Stock>(Arrays.asList(stocks));
        Stock stock = stockList.get(0);
        Quote quote = new Quote();
        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat ("MM.dd.yyyy hh:mm:ss a zzz");
        String d = ft.format(dNow);
        quote.setQuoteDate(d);
        quote.setL(stock.getL());
        quote.setE(stock.getE());
        quote.setT(stock.getT());
        quote.setName(stock.getName());
        String s = stock.getT();
        return quote;
    }

    @Override
    public Quote createQuote(Quote quote) {
        //quote.setQuoteID(1);
        entityManager.persist(quote);
        return quote;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Quote> getQuotesBySymbol(String t) {
       return entityManager.createQuery("from Quote where t = :t").setParameter("t", t).getResultList();
    }

    @Override
    public Stock getByTicker(String t) {
        return getPortfolioMap().get(t);
    }

    @Override
    public void update(Stock stock) {
        entityManager.merge(stock);
        return;
    }

    @Override
    public void delete(Stock stock) {
        if (entityManager.contains(stock))
            entityManager.remove(stock);
        else
            entityManager.remove(entityManager.merge(stock));
        return;
    }

//    @Override
//    public Stock addStock(Stock stock){
//        return new Stock();
//    };

    //Get Lists
    //==========

    @Override
    public List<Stock> getAllStocks(){
        //stockList = new ArrayList<Stock>(portfolio.values());
        List<Stock>stockList = getAll();
        return stockList;
    }

    @Override
    public List<Stock> search(String symbol){
        String base = "https://www.google.com/finance/info?infotype=infoquoteall&q=";
        String toReturn;
        String thisUri = (base + symbol);
        ResponseEntity<String> apiResponse = restTemplate.exchange(thisUri,
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
                });

        toReturn = apiResponse.getBody();
        String jsonStr = ("[" + toReturn);
        Gson gson = new Gson();
        Stock stocks[] = gson.fromJson(jsonStr, Stock[].class);
        ArrayList<Stock> stockList = new ArrayList<Stock>(Arrays.asList(stocks));
        return stockList;
    }



    @Override
    public List<Stock> addStock(String t){
        String base = "https://www.google.com/finance/info?infotype=infoquoteall&q=";

        String toReturn;
        String thisUri = (base + t);
        ResponseEntity<String> apiResponse = restTemplate.exchange(thisUri,
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
                });

        toReturn = apiResponse.getBody();
        String jsonStr = ("[" + toReturn);
        Gson gson = new Gson();
        Stock stocks[] = gson.fromJson(jsonStr, Stock[].class);
        ArrayList<Stock> stockList = new ArrayList<Stock>(Arrays.asList(stocks));
        Stock stock = stockList.get(0);
        if(portfolio.get(stock.getT()) == null){
            create(stock);
            portfolio.put(stock.getT(), stock);
            stockList = new ArrayList<Stock>(portfolio.values());
        }else
            portfolio = getPortfolioMap();
            stockList = new ArrayList<Stock>(portfolio.values());
        return stockList;
    }
    @Override
    public Stock getOnePortfolioStock(String symbol){
        Stock stock = new Stock();
        stock = portfolio.get(symbol);
        return stock;
    }
    @Override
    public List<Stock> removeStockFromPortfolio(String symbol){
        portfolio.remove(symbol);
        stockList = new ArrayList<Stock>(portfolio.values());
        return stockList;
    }
//    @Override
//    public void buyShares(int number, String name){
//        //int current = portfolio.get(name).getYourShares();
//        //portfolio.get(name).setYourShares(current + number);
//    }
    @Override
    public void takePortfolioSnapshot(){
        //HashMap<String, Stock> p = investor.getPortfolio();
        //int n = p.values().size();
        String d = new Date().toString();
        for(String s: portfolio.keySet()){
            portfolio.get(s);
            portfolioHistory.put(d, new HashMap<String, Stock>(portfolio));
        }

    }
}
