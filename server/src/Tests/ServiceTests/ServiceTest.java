package Tests.ServiceTests;
import DAOs.DAO;
import Services.DataAccessException;
import Services.Database;
import Services.Service;

public class ServiceTest {
    protected Service service;
    protected Database db;
    protected DAO dao;
    protected void setup() throws DataAccessException {
        db = new Database();
        db.openConnection();
        db.clearTables();
    }

    protected void teardown() throws DataAccessException {
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }
}
