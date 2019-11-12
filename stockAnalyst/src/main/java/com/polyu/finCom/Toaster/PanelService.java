package com.polyu.finCom.Toaster;

import com.polyu.finCom.Model.Stock;
import com.polyu.finCom.Model.StockInfo;

import java.util.List;
import java.util.Map;

public class PanelService {

    //获取下拉菜单ticker列表
    public List<String> getTickerList() {
        return null;
    }

    //根据ticker名称获取起始和终止日期
    public Map<String, String> getStartEndDate(String ticker) {
        return null;
    }

    //获取股票组合收益
    public List<StockInfo> getBatchInterest(List<StockInfo> condition) {
        return null;
    }

    //获取投资优化收益
    public List<StockInfo> getOptimization(List<StockInfo> condition) {
        return null;
    }
}
