package com.polyu.finCom.Model;

import java.text.DecimalFormat;

public class StockInfo {
    //股票代码
    private String ticker;
    //开始时间
    private String startDate;
    //结束时间
    private String endDate;
    //ra
    private Double returnRate;
    //权重
    private Double weight;
    //risk
    private Double risk;
    //β
    private Double beta;
    //α
    private Double alpha;
    //sharp ratio
    private Double sharpRatio;
    //
    private Double riskFree;

    public String getTicker() {
        return ticker;
    }

    public StockInfo setTicker(String ticker) {
        this.ticker = ticker;
        return this;
    }

    public Double getWeight() {
        return weight;
    }

    public StockInfo setWeight(Double weight) {
        this.weight = weight;
        return this;
    }

    public Double getAlpha() {
        DecimalFormat df = new DecimalFormat(".####");
        return Double.valueOf(df.format(alpha));
    }

    public StockInfo setAlpha(Double alpha) {
        this.alpha = alpha;
        return this;
    }

    public String getStartDate() {
        return startDate;
    }

    public StockInfo setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public String getEndDate() {
        return endDate;
    }

    public StockInfo setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public Double getReturnRate() {
        DecimalFormat df = new DecimalFormat(".####");
        return Double.valueOf(df.format(returnRate));
    }

    public StockInfo setReturnRate(Double returnRate) {
        this.returnRate = returnRate;
        return this;
    }

    public Double getRisk() {
        DecimalFormat df = new DecimalFormat(".####");
        return Double.valueOf(df.format(risk));
    }

    public StockInfo setRisk(Double risk) {
        this.risk = risk;
        return this;
    }

    public Double getBeta() {
        DecimalFormat df = new DecimalFormat(".####");
        return Double.valueOf(df.format(beta));
    }

    public StockInfo setBeta(Double beta) {
        this.beta = beta;
        return this;
    }

    public Double getSharpRatio() {
        DecimalFormat df = new DecimalFormat(".####");
        return Double.valueOf(df.format(sharpRatio));
    }

    public StockInfo setSharpRatio(Double sharpRatio) {
        this.sharpRatio = sharpRatio;
        return this;
    }

    public Double getRiskFree() {
        DecimalFormat df = new DecimalFormat(".####");
        return Double.valueOf(df.format(riskFree));
    }

    public StockInfo setRiskFree(Double riskFree) {
        this.riskFree = riskFree;
        return this;
    }
}
