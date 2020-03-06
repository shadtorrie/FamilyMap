package Tests.ServiceTests;

import DAOs.AuthDAO;
import DAOs.DAO;
import DAOs.PersonDAO;
import ModelsServer.*;
import Result.Clear;
import Result.Results;
import Services.ClearS;
import Services.DataAccessException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ClearSTest extends ServiceTest {
    @BeforeEach
    public void setup() throws DataAccessException {
        service = new ClearS();
        super.setup();
    }
    public Results clear(Model insertModel,String searchID) throws DataAccessException, SQLException {
        Results result = null;
        Model model = null;
        dao.insert(insertModel);
        db.closeConnection(true);
        result = service.requestService(new Request.Clear());
        db.openConnection();
        model = dao.find(searchID);
        assertNull(model,insertModel.getClass().toString()+" was not removed");
        return result;
    }
    @Test
    public void testClearEvent(){
        Result.Clear result = null;
        try {
            dao = new AuthDAO(db);
            String authID = "test1234";
            String username = "test1";
            result = (Clear) clear(new Auth(authID,username),authID);
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
        }
        assertNotNull(result,"Result was not received.");
        assertTrue(result.isSuccess(),"The clear should be successful");
        assertEquals("Clear succeeded.",result.getMessage(),"The error message was incorrect.");
    }

    @Test
    public void testClearAuth(){
        Result.Clear result = null;
        try {
            dao = new AuthDAO(db);
            String authID = "test1234";
            String username = "test1";
            result = (Clear) clear(new Auth(authID,username),authID);
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
        }
        assertNotNull(result,"Result was not received.");
        assertTrue(result.isSuccess(),"The clear should be successful");
        assertEquals("Clear succeeded.",result.getMessage(),"The error message was incorrect.");
    }
    @Test
    public void testClearPerson(){
        Result.Clear result = null;
        try {
            dao = new PersonDAO(db);
            String personID = "1234a";
            result = (Clear) clear(new Person(personID,"shadtorrie","first","last",
                    'm',"12345a", "123456a","1234567a"),personID);
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
        }
        assertNotNull(result,"Result was not received.");
        assertTrue(result.isSuccess(),"The clear should be successful");
        assertEquals("Clear succeeded.",result.getMessage(),"The error message was incorrect.");
    }
    @Test
    public void testClearUser(){
        Result.Clear result = null;
        try {
            dao = new PersonDAO(db);
            String username = "1234a";
            result = (Clear) clear(new User(username,"test","shad.torrie@gmail.com"),username);
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
        }
        assertNotNull(result,"Result was not received.");
        assertTrue(result.isSuccess(),"The clear should be successful");
        assertEquals("Clear succeeded.",result.getMessage(),"The error message was incorrect.");
    }
    @Test
    public void testClearEmpty(){
        Result.Clear result = null;
        try {
            db.closeConnection(true);
            result = (Clear) service.requestService(new Request.Clear());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        assertNotNull(result,"Result was not received.");
        assertTrue(result.isSuccess(),"The clear should be successful");
        assertEquals("Clear succeeded.",result.getMessage(),"The error message was incorrect.");
    }
    @AfterEach
    public void teardown() throws DataAccessException {
        super.teardown();
    }
}