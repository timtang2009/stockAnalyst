package com.polyu.finCom.Toaster;

import com.polyu.finCom.Mapper.StockMapper;
import com.polyu.finCom.Model.Stock;
import com.polyu.finCom.Model.StockInfo;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Toaster {

    private Calculator calculator = new Calculator();

    public String readFile(String path) {
        try {
            SqlSessionFactory sqlSessionFactory= GetSessionFactory.getSqlSessionFactory();
            SqlSession sqlSession = sqlSessionFactory.openSession(true);
            StockMapper mapper = sqlSession.getMapper(StockMapper.class);
            File filename = new File(path);
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            line = br.readLine();
            int lineNum = 0;
            double close = 0.0;
            while (line != null) {
                line = br.readLine();
                if (line != null && !line.startsWith("Ticker")) {
                    String[] datas = line.split(",");
                    if (datas.length != 8) {
                        throw new Exception("please check file in line " + lineNum);
                    }
                    Stock stock = new Stock();
                    stock.setTicker(datas[0])
                            .setDate(datas[1])
                            .setTime(datas[2])
                            .setOpen(datas[3])
                            .setHigh(datas[4])
                            .setLow(datas[5])
                            .setClose(datas[6])
                            .setVol(Integer.valueOf(datas[7]));
                    if (close != 0.0) {
                        stock.setReturnRate(String.valueOf(calculator.getInterest(Double.valueOf(datas[6]), close)));
                    } else {
                        stock.setReturnRate("0");
                    }
                    mapper.insert(stock);
                    close = Double.valueOf(datas[6]);
                }
                lineNum ++;
            }
            sqlSession.close();
        } catch (Exception e) {
//            logger.error("fail to read File from {}", path, e);
        }
        return null;
    }

    public void setMarketReturn() {
        SqlSessionFactory sqlSessionFactory= GetSessionFactory.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        StockMapper mapper = sqlSession.getMapper(StockMapper.class);
        List<String> ttlDates = mapper.getDatesByRange("19000101", "20210101");
        for (int i = 0; i < ttlDates.size(); i++) {
            Map<String, String> marketDaily = new HashMap<>();
            marketDaily.put("date", ttlDates.get(i));
            Integer dailyVol = mapper.getDailyVol(ttlDates.get(i));
            List<Stock> stocks = mapper.findStockByDate(ttlDates.get(i));
            double dailyRate = 0;
            if (stocks != null && stocks.size() > 0) {
                for (Stock stock : stocks) {
                    dailyRate += Double.valueOf(stock.getReturnRate()) * stock.getVol() / dailyVol;
                }
            }
            marketDaily.put("returnRate", String.valueOf(dailyRate));
            mapper.insertMarket(marketDaily);
        }
        sqlSession.close();
    }

    public void setCovariance(Map<String, List<Double>> datas) {

    }

//    public static void main(String[] args) throws Exception {
//        System.out.println("main函数开始执行");
//        Thread thread=new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("===task start===");
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("===task finish===");
//            }
//        });
//        thread.start();
//        System.out.println("main函数执行结束");
//    }


}
