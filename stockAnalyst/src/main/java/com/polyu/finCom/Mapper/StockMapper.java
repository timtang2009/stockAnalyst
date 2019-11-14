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
}
