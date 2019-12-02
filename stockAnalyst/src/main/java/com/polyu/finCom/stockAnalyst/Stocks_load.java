package com.polyu.finCom.stockAnalyst;

import com.polyu.finCom.Model.Stock;
import org.jfree.data.time.Day;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Stocks_load {
    private JFreeCandlestickChart jfreeCandlestickChart;
    private List<Stock> stocks;
    private int simulationTime;
    private ExecutorService executorService;

    public Stocks_load(JFreeCandlestickChart jfreeCandlestickChart, List<Stock> stocks, int simulationTime) {
        super();
        this.executorService = Executors.newCachedThreadPool();
        this.stocks = stocks;
        this.jfreeCandlestickChart = jfreeCandlestickChart;
        this.simulationTime = simulationTime;
    }

    public void run() {
        executorService.execute(() -> read());
    }

    private void read() {
        SimpleDateFormat standardDateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            if (stocks != null){
                for (Stock stock: stocks) {
                    Thread.sleep(simulationTime);
                    Date myDate = standardDateFormat.parse(stock.getDate());
                    //Day day = new Day(myDate);
                    
                    Double open = Double.parseDouble(stock.getOpen());
                    Double high = Double.parseDouble(stock.getHigh());
                    Double low = Double.parseDouble(stock.getLow());
                    Double close = Double.parseDouble(stock.getClose());
                    Long volume = Long.parseLong(stock.getVol().toString());

                    //Add trade to the jfreeCandlestickChart
                    jfreeCandlestickChart.addCandel(myDate.getTime(),open,high,low,close,volume);

                }

            }else {
                executorService.shutdown();
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }

    }
}
