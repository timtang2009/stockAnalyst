package com.polyu.finCom.Toaster;

import com.polyu.finCom.Mapper.StockMapper;
import com.polyu.finCom.Model.Stock;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class StockService {

    static StockMapper getMapper() {
        SqlSessionFactory sqlSessionFactory= GetSessionFactory.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        StockMapper mapper = sqlSession.getMapper(StockMapper.class);
        return mapper;
    }

    public Stock getStockById(Integer id) {
        return getMapper().findStockById(id);
    }

    public void createTable() {
        getMapper().createNewTable("stock");
    }
}
