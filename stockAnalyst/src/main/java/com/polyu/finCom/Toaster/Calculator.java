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
        average = average == null ? this.getExpectation(datas) : average;
        double variance = 0.0;
        for (Double p : datas) {
            double square = (average - p) * (average - p);
            variance = variance + square;
        }
        return variance / (datas.size() - 1);
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

    //求逆矩阵
    public double[][] getReverseMatrix(double[][] matrix, int n) {
        this.mrinv(matrix, n);
        return matrix;
    }

    public void printMatrix(double[][] a, int n){
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++)
            {
                System.out.print(a[i][j]+"  ");
            }
            System.out.println();
        }
    }

    ////////////////////////////////////////////////////////////////////////
    //函数：Mrinv
    //功能：求矩阵的逆
    //参数：n---整数，矩阵的阶数
    //a---Double型n*n二维数组，开始时为原矩阵，返回时为逆矩阵
    ////////////////////////////////////////////////////////////////////////
    public void mrinv(double[][] a, int n) {
        int i, j, row, col, k;
        double max, temp;
        int[] p = new int[n];
        double[][] b = new double[n][n];
        for (i = 0; i < n; i++) {
            p[i] = i;
            b[i][i] = 1;
        }

        for (k = 0; k < n; k++) {
            // 找主元
            max = 0;
            row = col = i;
            for (i = k; i < n; i++)
                for (j = k; j < n; j++) {
                    temp = Math.abs(b[i][j]);
                    if (max < temp) {
                        max = temp;
                        row = i;
                        col = j;
                    }
                }
            // 交换行列，将主元调整到 k 行 k 列上
            if (row != k) {
                for (j = 0; j < n; j++) {
                    temp = a[row][j];
                    a[row][j] = a[k][j];
                    a[k][j] = temp;
                    temp = b[row][j];
                    b[row][j] = b[k][j];
                    b[k][j] = temp;
                }
                i = p[row];
                p[row] = p[k];
                p[k] = i;
            }
            if (col != k) {
                for (i = 0; i < n; i++) {
                    temp = a[i][col];
                    a[i][col] = a[i][k];
                    a[i][k] = temp;
                }
            }
            // 处理
            for (j = k + 1; j < n; j++)
                a[k][j] /= a[k][k];
            for (j = 0; j < n; j++)
                b[k][j] /= a[k][k];
            a[k][k] = 1;

            for (j = k + 1; j < n; j++) {
                for (i = 0; i < k; i++)
                    a[i][j] -= a[i][k] * a[k][j];
                for (i = k + 1; i < n; i++)
                    a[i][j] -= a[i][k] * a[k][j];
            }
            for (j = 0; j < n; j++) {
                for (i = 0; i < k; i++)
                    b[i][j] -= a[i][k] * b[k][j];
                for (i = k + 1; i < n; i++)
                    b[i][j] -= a[i][k] * b[k][j];
            }
            for (i = 0; i < k; i++)
                a[i][k] = 0;
            a[k][k] = 1;
        }
        // 恢复行列次序；
        for (j = 0; j < n; j++)
            for (i = 0; i < n; i++)
                a[p[i]][j] = b[i][j];
    }

    //矩阵乘法
    public void mrcheng(double[][] a,double[][] b,double[][]c,int m,int n,int l)
    {
        double[][] d=new double[m][l];
        //使用中间变量d,是防止c=a或c=b的情形下计算出错
        int i,j,k;
        for(i=0;i<m;i++)
            for(j=0;j<l;j++)
            {
                d[i][j]=0;
                for(k=0;k<n;k++)
                    d[i][j]+=a[i][k]*b[k][j];
            }

        for(i=0;i<m;i++)
            for(j=0;j<l;j++)
                c[i][j]=d[i][j];
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
