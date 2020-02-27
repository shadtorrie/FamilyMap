package Tests.DAOTests;

import DAOs.DAO;
import Models.Model;
import Services.DataAccessException;
import Services.Database;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public abstract class DAOTest {
    protected Database db;
    protected Model model;
    protected DAO Dao;

    public Database getDb() {
        return db;
    }

    public void setDb(Database db) {
        this.db = db;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public abstract void setUp() throws Exception;
    public void tearDown() throws Exception{
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    public void insertPass() throws Exception {
        Model compareTest = null;
        try {
            db.openConnection();
            Dao.setDbConnection(db);
            Dao.insert(getModel());
            compareTest = Dao.find(model.getID());
        }
        catch(DataAccessException e){
            db.closeConnection(false);
            db.closeConnection(false);
            db = null;
        }
        finally{
            if(db!=null)
                db.closeConnection(true);
        }
        assertNotNull(compareTest);
        assertEquals(model,compareTest);
    }

    public void insertFail() throws Exception {
        Model compareTest = null;
        boolean inserted =true;
        try {
            db.openConnection();
            Dao.setDbConnection(db);
            Dao.insert(getModel());
            Dao.insert(getModel());
        }
        catch(DataAccessException e){
            db.closeConnection(false);
            inserted=false;
        }
        finally{
            db.closeConnection(true);
        }
        assertFalse(inserted);
        try{
            db.openConnection();
            compareTest = Dao.find(getModel().getID());
            db.closeConnection(true);
        }
        catch (DataAccessException e){
            db.closeConnection(false);
        }
        assertNull(compareTest);
    }

    public void findPass() throws Exception {
        Model compareTest = null;
        try {
            db.openConnection();
            Dao.setDbConnection(db);
            Dao.insert(getModel());
            compareTest = Dao.find(model.getID());
        }
        catch(DataAccessException e){
            db.closeConnection(false);
        }
        finally{
            db.closeConnection(true);
        }
        assertNotNull(compareTest);
    }

    public void findFail() throws Exception {
        Model compareTest = null;
        try {
            db.openConnection();
            Dao.setDbConnection(db);
            compareTest = Dao.find(model.getID());
        }
        catch(DataAccessException e){
            db.closeConnection(false);
            db.closeConnection(false);
        }
        finally{
            db.closeConnection(true);
        }
        assertNull(compareTest);
    }

    public void clear() throws Exception {
        ArrayList<Model> compareTest = null;
        try {
            db.openConnection();
            Dao.setDbConnection(db);
            Dao.insert(model);
            Dao.clear();
            compareTest = Dao.find();
        }
        catch(DataAccessException e){
            db.closeConnection(false);
        }
        finally{
            db.closeConnection(true);
        }
        assert compareTest != null;
        assertEquals(0,compareTest.size());
    }
}
