package Tests.DAOTests;

import DAOs.EventDAO;
import Models.Event;
import Models.User;
import Services.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventDAOTest extends DAOTest {

    @BeforeEach
    public void setUp() throws Exception {
        setDb(new Database());
        setModel(new Event("123A","test",123.1f,234.2f,"USA","Provo","Birth",1997));
    }
    @AfterEach
    public void tearDown() throws Exception {
        super.tearDown();
    }
    @Test
    public void insertPass() throws Exception {
        Dao = new EventDAO();
        super.insertPass();
    }
    @Test
    public void insertFail() throws Exception {
        Dao = new EventDAO();
        super.insertFail();
    }
    @Test
    public void findPass() throws Exception {
        Dao = new EventDAO();
        super.findPass();
    }
    @Test
    public void findFail() throws Exception {
        Dao = new EventDAO();
        super.findFail();
    }
    @Test
    public void clear() throws Exception {
        Dao = new EventDAO();
        super.clear();
    }
}