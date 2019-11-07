package com.polyu.finCom.Toaster;

import com.polyu.finCom.Mapper.StockMapper;
import com.polyu.finCom.Model.Stock;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Toaster {

    private static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

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
            while (line != null) {
                line = br.readLine();
                if (!line.startsWith("Ticker")) {
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
                            .setVol(datas[7]);
                    mapper.insert(stock);
                }
                lineNum ++;
            }
        } catch (Exception e) {
            logger.error("fail to read File from {}", path);
        }
        return null;
    }


}
