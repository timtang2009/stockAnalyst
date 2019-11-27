package com.polyu.finCom.Mapper;

import com.polyu.finCom.Model.Stock;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StockMapper {

    List<Stock> findStockByTicker(@Param("ticker") String ticker, @Param("start") String start, @Param("end") String end);

    List<Stock> findStockByDate(@Param("date") String date);

    void createNewTable(@Param("table") String table);


    void insert(Stock stock);

    List<String> getTickerList();

    Map<String, String> getStartEndDate(String ticker);

    List<Double> getCloseRecord(String ticker, String start, String end);

    Integer getVolByTicker(@Param("start") String start, @Param("end") String end, @Param("ticker") String ticker);

    Integer getFullVol(@Param("start") String start, @Param("end") String end);

    Integer getDailyVol(@Param("date")String date);

    Stock getStockByDate(@Param("date") String date, @Param("ticker") String ticker);

    List<String> getTickerInRange(@Param("start") String start, @Param("end") String end);

    List<String> getDatesByRange(@Param("start") String start, @Param("end") String end);

    List<Double> getReturnRatesByTickers(@Param("start") String start, @Param("end") String end);
}
