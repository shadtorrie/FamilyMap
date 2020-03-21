package Tests.ServiceTests;

import DAOs.AuthDAO;
import DAOs.DAO;
import DAOs.PersonDAO;
import Models.AuthModel;
import Models.PersonModel;
import Requests.PersonRequest;
import Results.PersonResult;
import Services.DataAccessException;
import Services.PeopleS;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;


class PeopleSTest extends ServiceTest {

    @BeforeEach
    public void setup() throws DataAccessException {
        service = new PeopleS();
        super.setup();
        dao = new PersonDAO(db);
    }
    @Test
    public void testFindPersonPass(){
        PersonResult results = null;
        try {
            String personID = "pers";
            String username = "test1";
            dao.insert(new PersonModel(personID,username,"Shad","Torrie","m"));
            DAO authDao = new AuthDAO(db);
            String authID = "test1234";
            authDao.insert(new AuthModel(authID,username));
            db.closeConnection(true);
            results = (PersonResult) service.requestService(new PersonRequest(personID, authID));
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
        }
        assertNotNull(results,"Result was not received.");
        assertTrue(results.isSuccess(),"The service didn't find the person.");
    }
    @Test
    public void testFindPersonMultiplePass(){
        Results.PersonList results = null;
        try {
            String personID = "pers";
            String personID2 = "pers2";
            String username = "test1";
            PersonModel firstPerson = new PersonModel(personID,username,"Shad","Torrie","m");
            PersonModel secondPerson = new PersonModel(personID2,username,"Shad","Torrie","m");
            dao.insert(firstPerson);
            dao.insert(secondPerson);
            DAO authDao = new AuthDAO(db);
            String authID = "test1234";
            authDao.insert(new AuthModel(authID,username));
            db.closeConnection(true);
            results = (Results.PersonList) service.requestService(new PersonRequest(authID));
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
        }
        assertNotNull(results,"Result was not received.");
        assertEquals(results.getData().size(),2,"The service didn't find the people.");
        Gson gson = new Gson();
        System.out.println(gson.toJson(results));
    }
    @Test
    public void testFindPersonFailAuth(){
        PersonResult results = null;
        try {
            String personID = "pers";
            dao.insert(new PersonModel(personID,"test","Shad","Torrie","m"));
            String authID = "test1234";
            String username = "test1";
            db.closeConnection(true);
            results = (PersonResult) service.requestService(new PersonRequest(personID, authID));
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
        }
        assertNotNull(results,"Result was not received.");
        assertFalse(results.isSuccess(),"The authentication token should be invalid");
        assertEquals("Error: Invalid auth token",results.getMessage(),"The error message was incorrect.");
    }
    @Test
    public void testFindPersonFail(){
        PersonResult results = null;
        try {
            DAO authDao = new AuthDAO(db);
            String authID = "test1234";
            String username = "test1";
            authDao.insert(new AuthModel(authID,username));
            db.closeConnection(true);
            results = (PersonResult) service.requestService(new PersonRequest("test123", authID));
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
        }
        assertNotNull(results,"Result was not received.");
        assertFalse(results.isSuccess(),"The person should not be found");
        assertEquals("Error: Invalid personID parameter",results.getMessage(),"The error message was incorrect.");
    }

    @AfterEach
    public void teardown() throws DataAccessException {
        super.teardown();
    }

}