package Tests.DAOTests;

import DAOs.UserDAO;
import Models.UserModel;
import Services.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserDAOTest extends DAOTest{
    @BeforeEach
    @Override
    public void setUp() throws Exception {
        setDb(new Database());
        setModel(new UserModel("testUser","Password123","test@gmail.com"));
    }
    @AfterEach
    public void tearDown() throws Exception {
        super.tearDown();
    }
    @Test
    public void insertPass() throws Exception {
        Dao = new UserDAO();
        super.insertPass();
    }
    @Test
    public void insertFail() throws Exception {
        Dao = new UserDAO();
        super.insertFail();
    }
    @Test
    public void findPass() throws Exception {
        Dao = new UserDAO();
        super.findPass();
    }
    @Test
    public void findFail() throws Exception {
        Dao = new UserDAO();
        super.findFail();
    }
    @Test
    public void clear() throws Exception {
        Dao = new UserDAO();
        super.clear();
    }

}