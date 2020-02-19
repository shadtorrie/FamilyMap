package Services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private Connection connection;

    /**
     *
     */
    public Database() {
    }

    /**
     *
     * @return
     */
    public Connection openConnection(){
        return null;
    }

    /**
     *
     * @return
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     *
     */
    public void closeConnection(){

    }
}
