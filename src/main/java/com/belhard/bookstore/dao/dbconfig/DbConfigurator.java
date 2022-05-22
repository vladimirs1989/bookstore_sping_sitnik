package com.belhard.bookstore.dao.dbconfig;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.module.FindException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConfigurator {
    private static final Logger logger = LogManager.getLogger(DbConfigurator.class);

    private static Connection connection;
    private static String url = "jdbc:postgresql://localhost:5432/bookstore_bh";
    private static String user = "postgres";
    private static String password = "root";

    //private static String url = "jdbc:postgresql://dumbo.db.elephantsql.com:5432/wgjfzuaq";
    //private static String user = "wgjfzuaq";
    //private static String password = "P3su0zE5akNlaT4T0CYmMFreqfz5NuSv";

    public static void initDbConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("not find driver - org.postgresql.Driver");
        }
        connection = DriverManager.getConnection(url, user, password);
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                initDbConnection();
                logger.info("CONNECTION IS SUCCESSFULLY");
            } catch (SQLException e) {
                logger.error("Not connection db");
            }
        }

        return connection;
    }

}

