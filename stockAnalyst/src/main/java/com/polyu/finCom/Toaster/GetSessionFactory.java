package com.polyu.finCom.Toaster;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

public class GetSessionFactory {
    private static SqlSessionFactory sqlSessionFactory;

    private GetSessionFactory() {

    }

    synchronized public static SqlSessionFactory getSqlSessionFactory() {
        if (sqlSessionFactory == null) {
            String resources = "com/polyu/finCom/mapper/mybatis-config.xml";
            InputStream inputStream = null;
            try {
                inputStream = Resources.getResourceAsStream(resources);
            } catch (Exception e) {
                e.printStackTrace();
            }
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        }
        return sqlSessionFactory;

    }
}
