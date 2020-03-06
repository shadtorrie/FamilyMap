package Tests.DAOTests;

import DAOs.AuthDAO;
import ModelsServer.Auth;
import Services.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class AuthDAOTest extends DAOTest{
    @BeforeEach
    @Override
    public void setUp() throws Exception {
        setDb(new Database());
        setModel(new Auth("1234567890","shadtorrie"));
    }
    @AfterEach
    public void tearDown() throws Exception {
        super.tearDown();
    }
    @Test
    public void insertPass() throws Exception {
        Dao = new AuthDAO();
        super.insertPass();
    }
    @Test
    public void insertFail() throws Exception {
        Dao = new AuthDAO();
        super.insertFail();
    }
    @Test
    public void findPass() throws Exception {
        Dao = new AuthDAO();
        super.findPass();
    }
    @Test
    public void findFail() throws Exception {
        Dao = new AuthDAO();
        super.findFail();
    }
    @Test
    public void clear() throws Exception {
        Dao = new AuthDAO();
        super.clear();
    }
}