package telkomedics.helper;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3435/medicare", "root", "Namnam2003!");
        return connection;
    }
}
