package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static Connection connection;

    private static String url = "jdbc:mysql://localhost/test?serverTimezone=UTC&useSSL=false";
    private static String user = "user";
    private static String password = "password";

    public static Connection getConnection() throws SQLException {
        if(connection != null) {
            return connection;
        }
        return DriverManager.getConnection(url, user, password);
    }
}
