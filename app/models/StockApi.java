package models;

/**
 * Created by cangulse on 4.07.2017.
 */
public class StockApi extends  Stock {
    public static Finder<Integer, Stock> find = new Stock.Finder<>(Integer.class, Stock.class);
}
