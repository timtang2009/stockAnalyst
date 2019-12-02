package com.polyu.finCom.Mapper;

import com.polyu.finCom.Model.Stock;
import com.polyu.finCom.Model.StockInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StockMapper {

    List<Stock> findStockByTicker(@Param("ticker") String ticker, @Param("start") String start, @Param("end") String end);

    List<Stock> findStockByDate(@Param("date") String date);

    void createNewTable(@Param("table") String table);

    void createMarketTable(@Param("table") String market);

    void createBetaTable(@Param("table") String table);

    void createIndex();

    void insert(Stock stock);

    String getCommonDates(@Param("stocks") List<String> stocks, @Param("date") String date);

    void insertBeta(StockInfo stock);

    String getBetaByDate(@Param("ticker") String ticker, @Param("start") String start, @Param("end") String end);

    String getMarketDailyReturn(String date);

    void insertMarket(Map<String, String> map);

    List<String> getTickerList();

    Double getReturnRatesByDate(@Param("date") String date, @Param("ticker") String ticker);

    Map<String, String> getStartEndDate(String ticker);

    List<Double> getCloseRecord(@Param("ticker") String ticker, @Param("start") String start, @Param("end") String end);

    Integer getVolByTicker(@Param("start") String start, @Param("end") String end, @Param("ticker") String ticker);

    Integer getFullVol(@Param("start") String start, @Param("end") String end);

    Integer getDailyVol(@Param("date")String date);

    Stock getStockByDate(@Param("date") String date, @Param("ticker") String ticker);

    List<String> getTickerInRange(@Param("start") String start, @Param("end") String end);

    List<String> getDatesByRange(@Param("start") String start, @Param("end") String end);

    List<Double> getReturnRatesByTickers(@Param("ticker") String ticker, @Param("start") String start, @Param("end") String end);
}
