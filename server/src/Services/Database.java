package Services;

import java.sql.*;

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
    public Connection openConnection() throws DataAccessException {
        try {
            if(connection==null) {
                final String connectionURL = "jdbc:sqlite:familymap.sqlite";
                connection = DriverManager.getConnection(connectionURL);
                connection.setAutoCommit(false);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Unable to open connection to Database");
        }
        return connection;
    }

    /**
     *
     * @return
     */
    public Connection getConnection() throws DataAccessException {
        if(connection==null){
            return openConnection();
        }
        return connection;
    }
    public PreparedStatement getPreparedStatement(String statement) throws DataAccessException {
        PreparedStatement returnStatement;
        try{
            returnStatement = connection.prepareStatement(statement);
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Unable to get prepared statement");
        }
        return returnStatement;
    }
    /**
     *
     * @param
     */
    public void closeConnection(boolean commit) throws DataAccessException {
        if(connection==null){
            return;
        }
        try{
            if(commit){
                connection.commit();
            }
            else{
                connection.rollback();
            }
            connection.close();
            connection=null;
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Unable to close database connection");
        }
    }

    public void clearTables() throws DataAccessException {
        try(Statement statement = connection.createStatement()){
            String sql="DELETE FROM Users";
            statement.executeUpdate(sql);
            sql="DELETE FROM Event";
            statement.executeUpdate(sql);
            sql="DELETE FROM Person";
            statement.executeUpdate(sql);
            sql="DELETE FROM Authorization_Token";
            statement.executeUpdate(sql);
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("SQL error while clearing tables.");
        }

    }
}
