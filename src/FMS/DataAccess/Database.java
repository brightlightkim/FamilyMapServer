package DataAccess;

import java.sql.Connection;
import java.sql.SQLException;

public class Database {
    Connection connection;

    public Database(){
    }

    Database (Connection conn){
        connection = conn;
    }

    public Connection getConnection() {
        return connection;
    }

    public void clearTables() {

    }

    public void closeConnection(boolean b) throws SQLException {
        if(!b){
            connection.rollback();
        }
        connection.close();
    }
}
