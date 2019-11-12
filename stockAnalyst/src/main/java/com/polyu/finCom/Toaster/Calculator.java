package com.polyu.finCom.Toaster;

import java.math.BigDecimal;
import java.util.List;

public class Calculator {

    //计算均值
    public BigDecimal getExpectation(List<BigDecimal> datas) {
        BigDecimal sum = BigDecimal.ZERO;
        for (BigDecimal data : datas) {
            sum = sum.add(data);
        }
        return sum.divide(new BigDecimal(datas.size()));
    }

    //计算收益率
    public double getInterest(double t, double t_1) {
        return Math.log(t) - Math.log(t_1);
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
    public BigDecimal getVariance(List<BigDecimal> datas) {
        BigDecimal average = this.getExpectation(datas);

        BigDecimal variance = BigDecimal.ZERO;
        for (BigDecimal p : datas) {
            BigDecimal square = average.subtract(p).multiply(average.subtract(p));
            variance = variance.add(square);
        }
        return variance.divide(new BigDecimal(datas.size()));
    }

    //计算标准差
    public BigDecimal getStandard(List<BigDecimal> datas) {
        double st = Math.sqrt(this.getVariance(datas).doubleValue());
        return new BigDecimal(st);
    }

    //计算协方差
    public double covariance(double[] v1, double[] v2) {
        if (v1.length != v2.length)
            throw new IllegalArgumentException(
                    "Arrays 长度必须相同 : " + v1.length
                            + ", " + v2.length);
        final double m1 = mean(v1);
        final double m2 = mean(v2);
        double ans = 0.0;
        for (int i = 0; i < v1.length; i++)
            ans += (v1[i] - m1) * (v2[i] - m2);
        return ans / (v1.length - 1);
    }

    private double mean(double[] v) {
        return (mass(v) / (double) v.length);
    }

    private double mass(double[] v) {
        double somme = 0.0;
        for (int k = 0; k < v.length; k++) {
            somme += v[k];
        }
        return (somme);
    }
}
