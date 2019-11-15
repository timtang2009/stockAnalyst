package com.polyu.finCom.Toaster;

import com.polyu.finCom.Mapper.StockMapper;
import com.polyu.finCom.Model.Stock;
import com.polyu.finCom.Model.StockInfo;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class
PanelService {

    private Calculator calculator = new Calculator();

    //获取下拉菜单ticker列表
    public List<String> getTickerList() {
        SqlSession sqlSession = getSession();
        StockMapper mapper = sqlSession.getMapper(StockMapper.class);
        List<String> list = mapper.getTickerList();
        sqlSession.close();
        return list;
    }

    //根据ticker名称获取起始和终止日期
    public Map<String, String> getStartEndDate(String ticker) {
        SqlSession sqlSession = getSession();
        StockMapper mapper = sqlSession.getMapper(StockMapper.class);
        Map<String, String> map = mapper.getStartEndDate(ticker);
        sqlSession.close();
        return map;
    }

    public StockInfo getStockInfo(String ticker, String start, String end, Double riskFree) {
        SqlSession sqlSession = getSession();
        StockMapper mapper = sqlSession.getMapper(StockMapper.class);
        StockInfo stockInfo = new StockInfo();
        List<Stock> stocks = mapper.findStockByTicker(ticker, start, end);
        stockInfo.setTicker(ticker)
                .setStartDate(start)
                .setEndDate(end);
        stockInfo = calculator.AveIntRate(stockInfo, stocks.stream()
                .map(Stock::getClose)
                .collect(Collectors.toList()));
        stockInfo.setSharpRatio((stockInfo.getReturnRate() - riskFree) / stockInfo.getRisk());
        stockInfo.setRiskFree(riskFree);
        return stockInfo;

    }

    //大盘第t天收益率
    public double batchReturnRate(String date) {
//        List<Stock> stockToday =
        return 0;
    }

    //获取股票组合收益
    public List<StockInfo> getBatchInterest(List<StockInfo> condition) {
        return null;
    }

    //获取投资优化收益
    public List<StockInfo> getOptimization(List<StockInfo> condition) {
        return null;
    }

    private SqlSession getSession() {
        SqlSessionFactory sqlSessionFactory= GetSessionFactory.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        return sqlSession;
    }
}
