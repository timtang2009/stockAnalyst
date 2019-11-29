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

    public List<Stock> getStockList(String ticker, String startDate, String endDate) {
        SqlSession sqlSession = getSession();
        StockMapper mapper = sqlSession.getMapper(StockMapper.class);
        List<Stock> stockList = mapper.findStockByTicker(ticker, startDate, endDate);
        sqlSession.close();
        return stockList;
    }

    //calculate the daily return rate market portfolio
    public double getMReturnRate(String date) {
        SqlSession sqlSession = getSession();
        StockMapper mapper = sqlSession.getMapper(StockMapper.class);
        String dailyReturn = mapper.getMarketDailyReturn(date);
        sqlSession.close();
        return dailyReturn == null ? null : Double.valueOf(dailyReturn);
    }

    //calculate the risk of market portfolio
    public double getMarketRisk(String start, String end) {
        SqlSession sqlSession = getSession();
        StockMapper mapper = sqlSession.getMapper(StockMapper.class);
        List<String> dates = mapper.getDatesByRange(start, end);
        List<Double> returnRates = new ArrayList<>();
        for (String date : dates) {
            returnRates.add(this.getMReturnRate(date));
        }
        sqlSession.close();
        return calculator.getVariance(returnRates,null);
    }

    //calculate the beta of each stock
    public double getStockBeta(String ticker, String start, String end) {
        SqlSession sqlSession = getSession();
        StockMapper mapper = sqlSession.getMapper(StockMapper.class);
        String beta = mapper.getBetaByDate(ticker, start, end);
        if (beta != null && beta.length() > 0) {
            sqlSession.close();
            return Double.valueOf(beta);
        } else {
            List<String> dates = mapper.getDatesByRange(start, end);
            List<Double> stockData = new ArrayList<>();
            List<Double> marketData = new ArrayList<>();
            for (int i = 0; i < dates.size(); i++) {
                Stock stock = mapper.getStockByDate(dates.get(i), ticker);
                if (stock != null) {
                    stockData.add(Double.valueOf(stock.getReturnRate()));
                    marketData.add(this.getMReturnRate(dates.get(i)));
                }
            }
            StockInfo stockInfo = new StockInfo();
            double stockBeta = calculator.covariance(stockData, marketData) / this.getMarketRisk(start, end);
            stockInfo.setTicker(ticker)
                    .setStartDate(start)
                    .setEndDate(end)
                    .setBeta(stockBeta);
            mapper.insertBeta(stockInfo);
            sqlSession.close();
            return stockBeta;
        }
    }

    public List<StockInfo> getMatrix(List<StockInfo> condition) {
        SqlSession sqlSession = getSession();
        StockMapper mapper = sqlSession.getMapper(StockMapper.class);
        List<List<Double>> retData = new ArrayList<>();
        List<Double> eFactor = new ArrayList<>();
        List<String> conditionStocks = condition.stream()
                .filter(s -> !"riskFree".equals(s.getTicker()))
                .map(StockInfo::getTicker)
                .collect(Collectors.toList());
        List<String> dates = mapper.getCommonDates(conditionStocks);
        for (StockInfo stockInfo : condition) {
            if (!"riskFree".equals(stockInfo.getTicker())) {
                List<Double> stockRates = new ArrayList<>();
                for (String date : dates) {
                    stockRates.add(mapper.getReturnRatesByDate(date, stockInfo.getTicker()));
                }
                retData.add(stockRates);
            } else {
                List<Double> riskFreeRates = new ArrayList<>();
                for (int i = 0; i < dates.size(); i++)
                    riskFreeRates.add(stockInfo.getRiskFree());
                retData.add(riskFreeRates);
            }
            eFactor.add(stockInfo.getReturnRate() - stockInfo.getRiskFree());
        }
        double[][] covRates = new double[condition.size()][condition.size()];
        if (retData.size() > 0) {
            for (int i = 0; i < condition.size(); i++) {
                for (int j = 0; j < condition.size(); j++) {
                    covRates[i][j] = calculator.covariance(retData.get(i), retData.get(j));
                }
            }
        }
        double[][] retCov = calculator.getReverseMatrix(covRates, condition.size());
        List<StockInfo> reCondition = new ArrayList<>();
        List<Double> weights = new ArrayList<>();
        double sum = 0;
        for (int i = 0; i < condition.size(); i++) {
            double slot = 0;
            for (int j = 0; j < condition.size(); j++) {
                slot += retCov[i][j] * eFactor.get(i);
            }
            weights.add(slot);
            sum += slot;
        }
        for (int i = 0; i < condition.size(); i++) {
            condition.get(i).setWeight(weights.get(i) / sum);
        }
        sqlSession.close();
        return condition;
    }

    //calculate the alpha of each stock
    public double getStockAlpha(String ticker, String start, String end, Double riskFree) {
        return (1 - this.getStockBeta(ticker, start, end)) * riskFree;
    }

    //获取股票组合收益
    public List<StockInfo> getBatchInterest(List<StockInfo> condition) {
        List<StockInfo> result = new ArrayList<>();
        double resurnRate = 0, alpha = 0, beta = 0;
        for (StockInfo stockInfo : condition) {
            StockInfo info = new StockInfo();
            if (!"riskFree".equals(stockInfo.getTicker())) {
                info = getStockInfo(stockInfo.getTicker(), stockInfo.getStartDate(), stockInfo.getEndDate(), stockInfo.getRiskFree());
                info.setBeta(this.getStockBeta(stockInfo.getTicker(), stockInfo.getStartDate(), stockInfo.getEndDate()));
                info.setAlpha((1 - info.getBeta()) * stockInfo.getRiskFree());
                info.setWeight(stockInfo.getWeight());
            } else {
                info = stockInfo;
                info.setReturnRate(info.getRiskFree());
                info.setAlpha(info.getRiskFree())
                        .setBeta(0.0);
            }
            result.add(info);
            resurnRate += info.getWeight() * info.getReturnRate();
            alpha += info.getWeight() * info.getAlpha();
            beta += info.getWeight() * info.getBeta();
        }
        StockInfo overall = new StockInfo();
        overall.setBeta(beta)
                .setAlpha(alpha)
                .setRisk(Math.sqrt(this.getMarketRisk(condition.get(0).getStartDate(), condition.get(0).getEndDate())))
                .setReturnRate(resurnRate)
                .setWeight(1.0)
                .setTicker("Portfolio");
        result.add(overall);
        return result;
    }

    //获取投资优化收益
    public List<StockInfo> getOptimization(List<StockInfo> condition) {
        return this.getBatchInterest(this.getMatrix(condition));
    }

    private SqlSession getSession() {
        SqlSessionFactory sqlSessionFactory= GetSessionFactory.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        return sqlSession;
    }

}
