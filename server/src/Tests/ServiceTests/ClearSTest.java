package Tests.ServiceTests;

import DAOs.AuthDAO;
import DAOs.PersonDAO;
import Models.*;
import Requests.ClearRequest;
import Results.ClearResult;
import Results.Results;
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
        result = service.requestService(new ClearRequest());
        db.openConnection();
        model = dao.find(searchID);
        assertNull(model,insertModel.getClass().toString()+" was not removed");
        return result;
    }
    @Test
    public void testClearEvent(){
        ClearResult result = null;
        try {
            dao = new AuthDAO(db);
            String authID = "test1234";
            String username = "test1";
            result = (ClearResult) clear(new AuthModel(authID,username),authID);
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
        }
        assertNotNull(result,"Result was not received.");
        assertTrue(result.isSuccess(),"The clear should be successful");
        assertEquals("Clear succeeded.",result.getMessage(),"The error message was incorrect.");
    }

    @Test
    public void testClearAuth(){
        ClearResult result = null;
        try {
            dao = new AuthDAO(db);
            String authID = "test1234";
            String username = "test1";
            result = (ClearResult) clear(new AuthModel(authID,username),authID);
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
        }
        assertNotNull(result,"Result was not received.");
        assertTrue(result.isSuccess(),"The clear should be successful");
        assertEquals("Clear succeeded.",result.getMessage(),"The error message was incorrect.");
    }
    @Test
    public void testClearPerson(){
        ClearResult result = null;
        try {
            dao = new PersonDAO(db);
            String personID = "1234a";
            result = (ClearResult) clear(new PersonModel(personID,"shadtorrie","first","last",
                    "m","12345a", "123456a","1234567a"),personID);
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
        }
        assertNotNull(result,"Result was not received.");
        assertTrue(result.isSuccess(),"The clear should be successful");
        assertEquals("Clear succeeded.",result.getMessage(),"The error message was incorrect.");
    }
    @Test
    public void testClearUser(){
        ClearResult result = null;
        try {
            dao = new PersonDAO(db);
            String username = "1234a";
            result = (ClearResult) clear(new UserModel(username,"test","shad.torrie@gmail.com"),username);
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
        }
        assertNotNull(result,"Result was not received.");
        assertTrue(result.isSuccess(),"The clear should be successful");
        assertEquals("Clear succeeded.",result.getMessage(),"The error message was incorrect.");
    }
    @Test
    public void testClearEmpty(){
        ClearResult result = null;
        try {
            db.closeConnection(true);
            result = (ClearResult) service.requestService(new ClearRequest());
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