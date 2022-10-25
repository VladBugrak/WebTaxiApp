package com.taxi.model.dao;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

public class ConnectionPoolHolder {

    final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    final static String DB_URL = "jdbc:mysql://localhost:3306/taxi_db";
    final static String DB_USER = "root";
    final static String DB_PASSWORD = "root";



        private static volatile DataSource dataSource;
        static DataSource getDataSource(){

            if (dataSource == null){
                synchronized (ConnectionPoolHolder.class) {
                    if (dataSource == null) {
                        BasicDataSource basicDataSource = new BasicDataSource();
                        basicDataSource.setDriverClassName(JDBC_DRIVER);
                        basicDataSource.setUrl(DB_URL);
                        basicDataSource.setUsername(DB_USER);
                        basicDataSource.setPassword(DB_PASSWORD);
                        basicDataSource.setMinIdle(5);
                        basicDataSource.setMaxIdle(10);
                        basicDataSource.setMaxOpenPreparedStatements(100);
                        basicDataSource.setDefaultAutoCommit(false);
                        dataSource = basicDataSource;
                    }
                }
            }
            return dataSource;

        }


}
