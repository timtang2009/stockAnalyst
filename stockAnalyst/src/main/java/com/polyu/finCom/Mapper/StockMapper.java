package com.polyu.finCom.Mapper;

import com.polyu.finCom.Model.Stock;
import org.apache.ibatis.annotations.Param;

public interface StockMapper {

    Stock findStockById(Integer id);

    void createNewTable(@Param("table") String table);

    void insert();
}
