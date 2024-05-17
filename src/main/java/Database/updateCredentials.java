package Database;

import java.sql.Connection;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;

public class updateCredentials {
    DatabaseInstance database = DatabaseInstance.getInstance("jdbc:mysql://localhost:3306/quackstagram_db","root","marcel");
    Connection conn = database.getConn();
    public void createUser(String username, String password, String bio, String photoDir) throws SQLException {

    }
}
