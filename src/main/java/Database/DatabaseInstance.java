package Database;

import java.sql.*;

public class DatabaseInstance {
    private static DatabaseInstance instance;
    String url;
    String username;
    String password;

    private DatabaseInstance(String url, String username, String password){
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public static DatabaseInstance getInstance(String url, String username, String password){
        if(instance == null){
            instance = new DatabaseInstance(url,username,password);
        }  return instance;
    }

    public Connection getConn() {
        Connection conn;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

}
