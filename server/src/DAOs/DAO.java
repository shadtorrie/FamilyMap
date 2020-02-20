package DAOs;

import Models.Model;
import Services.DataAccessException;
import Services.Database;

import java.sql.SQLException;
import java.util.ArrayList;

public abstract class DAO {
    private Database dbConnection;
    public DAO(Database database){
        dbConnection = database;
    }
    public DAO(){
    }

    public abstract Model insert(Model insertModel) throws DataAccessException, SQLException;
    public abstract Model find(String searchString) throws DataAccessException;
    public abstract ArrayList<Model> find() throws DataAccessException;
    public abstract void clear() throws DataAccessException;

    public Database getDbConnection() {
        return dbConnection;
    }

    public void setDbConnection(Database dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
