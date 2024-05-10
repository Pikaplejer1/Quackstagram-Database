package Database;

import java.sql.*;

public class DatabaseInstance {
    private static DatabaseInstance instance;
    private DatabaseInstance(String url, String username, String password){
        connect(url,username,password);
    }

    public static DatabaseInstance getInstance(String url, String username, String password){
        if(instance == null){
            instance = new DatabaseInstance(url,username,password);
        }  return instance;
    }

    public static Connection connect(String url, String username, String password) {
        Connection conn;
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }
}
