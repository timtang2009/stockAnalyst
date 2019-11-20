package com.polyu.finCom.Toaster;

import com.polyu.finCom.Model.StockInfo;

import java.util.ArrayList;
import java.util.List;

public class Calculator {

    //计算均值
    public Double getExpectation(List<Double> datas) {
        double sum = 0.0;
        for (Double data : datas) {
            sum += data;
        }
        return sum / datas.size();
    }

    //计算收益率
    public double getInterest(double t, double t_1) {
        return Math.log(t) - Math.log(t_1);
    }

    //计算平均收益率
    public StockInfo AveIntRate(StockInfo stockInfo, List<String> rates) {
        if (rates == null || rates.size() < 2)
            return null;
        double sum = 0.0;
        List<Double> dailyRR = new ArrayList<>();
        for (int i = 0; i < rates.size() - 1; i++) {
            double dailyRate = this.getInterest(Double.valueOf(rates.get(i + 1)), Double.valueOf(rates.get(i)));
            dailyRR.add(dailyRate);
            sum += dailyRate;
        }
        stockInfo.setReturnRate(sum / (rates.size() - 1));
        stockInfo.setRisk(this.getStandard(dailyRR, sum / (rates.size() - 1)));
        return stockInfo;
    }

    //计算组合收益率
    public double getBatchInterest(List<Double> ins, List<Double> weighs) {
        double batchInterest = 0.0;
        for (int i = 0; i < ins.size(); i++) {
            batchInterest += ins.get(i) * weighs.get(i);
        }
        return batchInterest;
    }

    //计算方差
    public Double getVariance(List<Double> datas, Double average) {
        average = average == null ? this.getExpectation(datas): average;
        double variance = 0.0;
        for (Double p : datas) {
            double square = (average - p) * (average - p);
            variance = variance + square;
        }
        return variance / datas.size();
    }

    //计算标准差
    public Double getStandard(List<Double> datas, Double average) {
        double st = Math.sqrt(this.getVariance(datas, average));
        return st;
    }

    //计算协方差
    public double covariance(List<Double> v1, List<Double> v2) {
        if (v1.size() != v2.size())
            throw new IllegalArgumentException(
                    "Arrays 长度必须相同 : " + v1.size()
                            + ", " + v2.size());
        final double m1 = mean(v1);
        final double m2 = mean(v2);
        double ans = 0.0;
        for (int i = 0; i < v1.size(); i++)
            ans += (v1.get(i) - m1) * (v2.get(i) - m2);
        return ans / (v1.size() - 1);
    }

    private double mean(List<Double> v) {
        return (mass(v) / (double) v.size());
    }

    private double mass(List<Double> v) {
        double somme = 0.0;
        for (int k = 0; k < v.size(); k++) {
            somme += v.get(k);
        }
        return (somme);
    }
}
