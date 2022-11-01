package com.taxi.model.dao;

import com.taxi.util.DatabaseManager;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

public class ConnectionPoolHolder {

    final static String JDBC_DRIVER = DatabaseManager.getProperty("jdbc_driver");
    final static String DB_URL = DatabaseManager.getProperty("db_url");
    final static String DB_USER = DatabaseManager.getProperty("db_user");
    final static String DB_PASSWORD = DatabaseManager.getProperty("db_password");
    final static int MIN_IDLE = Integer.parseInt(DatabaseManager.getProperty("min_idle"));
    final static int MAX_IDLE = Integer.parseInt(DatabaseManager.getProperty("max_idle"));
    final static int MAX_OPEN_PREPARED_STATEMENTS = Integer.parseInt(DatabaseManager.getProperty("max_open_prepared_statements"));
    final static boolean  DEFAULT_AUTO_COMMIT = Boolean.parseBoolean(DatabaseManager.getProperty("default_auto_commit"));


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
                        basicDataSource.setMinIdle(MIN_IDLE);
                        basicDataSource.setMaxIdle(MAX_IDLE);
                        basicDataSource.setMaxOpenPreparedStatements(MAX_OPEN_PREPARED_STATEMENTS);
                        basicDataSource.setDefaultAutoCommit(DEFAULT_AUTO_COMMIT);
                        dataSource = basicDataSource;
                    }
                }
            }
            return dataSource;

        }


}
