package com.polyu.finCom.Toaster;

import com.polyu.finCom.Mapper.StockMapper;
import com.polyu.finCom.Model.Stock;
import com.polyu.finCom.Model.StockInfo;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.ArrayList;
import java.util.HashMap;
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

    //第二个页面获取ticker info
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
        sqlSession.close();
        return stockInfo;

    }

    //calculate the sharp ratio of portfolio
    public double getSRPort(List<StockInfo> stockInfoList, Double riskFree) {
        return (this.getPortRR(stockInfoList) - riskFree) / Math.sqrt(this.getPortRisk(stockInfoList));
    }

    //calculate the risk of portfolio
    public double getPortRisk(List<StockInfo> stockInfoList) {
        double portRisk = 0.0;
        SqlSession sqlSession = getSession();
        StockMapper mapper = sqlSession.getMapper(StockMapper.class);
        Map<String, List<Double>> record = new HashMap<>();
        for(int i = 0; i < stockInfoList.size(); i++) {
            StockInfo stockInfo = this.getStockInfo(stockInfoList.get(i).getTicker(), stockInfoList.get(i).getStartDate(),
                    stockInfoList.get(i).getEndDate(), stockInfoList.get(i).getRiskFree());
            portRisk += stockInfo.getWeight() * stockInfo.getWeight() * stockInfo.getRisk() * stockInfo.getRisk();
            List<Double> recordData1 = mapper.getCloseRecord(stockInfoList.get(i).getTicker(), stockInfoList.get(i).getStartDate(),
                    stockInfoList.get(i).getEndDate());
            if (recordData1 != null && recordData1.size() > 0) {
                record.put(stockInfo.getTicker(), recordData1);
            }
            for (int j = 0; j < stockInfoList.size(); j++) {
                if (record.get(stockInfoList.get(i).getTicker()) != null) {
                    portRisk += 2 * stockInfoList.get(i).getWeight() * stockInfoList.get(j).getWeight()
                            * calculator.covariance(recordData1, record.get(stockInfoList.get(i).getTicker()));
                } else {
                    StockInfo stockInfo2 = this.getStockInfo(stockInfoList.get(j).getTicker(), stockInfoList.get(j).getStartDate(),
                            stockInfoList.get(j).getEndDate(), stockInfoList.get(j).getRiskFree());
                    List<Double> recordData2 = mapper.getCloseRecord(stockInfoList.get(j).getTicker(), stockInfoList.get(j).getStartDate(),
                            stockInfoList.get(j).getEndDate());
                    if (recordData2 != null && recordData2.size() > 0) {
                        record.put(stockInfo2.getTicker(), recordData2);
                    }
                    portRisk += 2 * stockInfoList.get(i).getWeight() * stockInfoList.get(j).getWeight()
                            * calculator.covariance(recordData1, recordData2);
                }

            }
        }
        sqlSession.close();
        return portRisk;

    }

    //calculate the Average Return Rate of portfolio
    public double getPortRR(List<StockInfo> stockInfoList) {
        double returnRate = 0.0;
        for (int i = 0; i < stockInfoList.size(); i++) {
            returnRate += stockInfoList.get(i).getReturnRate() * stockInfoList.get(i).getWeight();
        }
        return returnRate;
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
