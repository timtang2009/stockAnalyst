package com.polyu.finCom.Model;

public class Stock {

    private Integer id;
    private String ticker;
    private String date;
    private String time;
    private String open;
    private String high;
    private String low;
    private String close;
    private String returnRate;
    private Integer vol;

    public Integer getId() {
        return id;
    }

    public Stock setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getReturnRate() {
        return returnRate;
    }

    public Stock setReturnRate(String returnRate) {
        this.returnRate = returnRate;
        return this;
    }

    public String getTicker() {
        return ticker;
    }

    public Stock setTicker(String ticker) {
        this.ticker = ticker;
        return this;
    }

    public String getDate() {
        return date;
    }

    public Stock setDate(String date) {
        this.date = date;
        return this;
    }

    public String getTime() {
        return time;
    }

    public Stock setTime(String time) {
        this.time = time;
        return this;
    }

    public String getOpen() {
        return open;
    }

    public Stock setOpen(String open) {
        this.open = open;
        return this;
    }

    public String getHigh() {
        return high;
    }

    public Stock setHigh(String high) {
        this.high = high;
        return this;
    }

    public String getLow() {
        return low;
    }

    public Stock setLow(String low) {
        this.low = low;
        return this;
    }

    public String getClose() {
        return close;
    }

    public Stock setClose(String close) {
        this.close = close;
        return this;
    }

    public Integer getVol() {
        return vol;
    }

    public Stock setVol(Integer vol) {
        this.vol = vol;
        return this;
    }
}
