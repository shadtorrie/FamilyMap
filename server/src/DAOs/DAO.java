package DAOs;

import Models.Model;
import Services.Database;

public abstract class DAO {
    private Database dbConnection;
    public DAO(Database database){
        dbConnection = database;
    }
    public abstract Model insert(Model insertModel);
    public abstract Model find(String searchString);
    public abstract void clear();
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
