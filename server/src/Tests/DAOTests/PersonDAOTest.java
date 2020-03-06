package Tests.DAOTests;

import DAOs.PersonDAO;
import ModelsServer.Person;
import Services.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonDAOTest extends DAOTest{
    @BeforeEach
    @Override
    public void setUp() throws Exception {
        setDb(new Database());
        setModel(new Person("1234a","shadtorrie","first","last",
                "m","12345a", "123456a","1234567a"));
    }
    @AfterEach
    public void tearDown() throws Exception {
        super.tearDown();
    }
    @Test
    public void insertPass() throws Exception {
        Dao = new PersonDAO();
        super.insertPass();
    }
    @Test
    public void insertFail() throws Exception {
        Dao = new PersonDAO();
        super.insertFail();
    }
    @Test
    public void findPass() throws Exception {
        Dao = new PersonDAO();
        super.findPass();
    }
    @Test
    public void findFail() throws Exception {
        Dao = new PersonDAO();
        super.findFail();
    }
    @Test
    public void clear() throws Exception {
        Dao = new PersonDAO();
        super.clear();
    }
}