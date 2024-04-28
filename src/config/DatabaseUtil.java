package config;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

    private static Connection connection;

    public static void connect() throws SQLException {
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

        String url = "jdbc:mySQL://pgonevn.com/quanlibanve";
        String userName = "root";
        String password = "7wAXtU5DLUqrbq5pEFEAxQ";

        connection = DriverManager.getConnection(url, userName, password);
    }

    public static void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
    public static Connection getConnection() {
        return connection;
    }
}
