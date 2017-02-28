package pgoggin.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by patrickgoggin on 2/27/17.
 */

public interface StockDao {// Single Stock CRUD
    //======================
    public Stock getStockByID(int stockID);
    public List<Stock> getAll();
    public HashMap<String, Stock> getPortfolioMap();
    public void create(Stock stock);
    public Quote createQuote(Quote quote);
    public Quote newQuote(String t);
    public Stock getByTicker(String t);
    public void update(Stock stock);
    public void delete(Stock stock);
    public List<Quote> getQuotesBySymbol(String t);
    public void recordQuotes();
    public double getFunds();
    public double addFunds(double funds);
    //public Transaction buyShares(Transaction t);
//    public void changeStockStatus(int status, int stockID);

//    public void removeStock(int stockID);
//
//    public void editStock(Stock stock);

    //public Stock addStock(Stock stock);
    public List<Stock> accessAPI();
    public List<Stock> search(String symbol);
    public List<Stock> addStock(String t);
    public Stock getOnePortfolioStock(String symbol);
    public List<Stock> removeStockFromPortfolio(String symbol);
    public Transaction buyShares(Transaction t);
    public Transaction sellShares(Transaction t);
    public void takePortfolioSnapshot();

    //Get Lists
    //==========
    public List<Stock> getAllStocks();
//    public List<Stock> getActiveStocks();
//
//    public List<Stock> getFutureStocks();
//
//    public List<Stock> getStocksForReview();
//
//    public List<Stock> getStaticPageHeaders();

    //Hashtags
    //=========
//    public String grabHashtags(String stockBody);
//
//    public ArrayList<String> getAllHashTags();
//
//    public List<Stock> searchByHashTag(String hashtag);
//
//    public ArrayList<Hashtag> countHashtags();

    //Test Methods
    //============
//    public void populateTestData ();
//
//    public void resetTestData();
}
