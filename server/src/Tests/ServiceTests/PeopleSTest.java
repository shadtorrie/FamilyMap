package Tests.ServiceTests;

import DAOs.AuthDAO;
import DAOs.DAO;
import DAOs.PersonDAO;
import ModelsServer.Auth;
import ModelsServer.Person;
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
        Result.Person results = null;
        try {
            String personID = "pers";
            String username = "test1";
            dao.insert(new Person(personID,username,"Shad","Torrie",'M'));
            DAO authDao = new AuthDAO(db);
            String authID = "test1234";
            authDao.insert(new Auth(authID,username));
            db.closeConnection(true);
            results = (Result.Person) service.requestService(new Request.Person(personID, authID));
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
        }
        assertNotNull(results,"Result was not received.");
        assertTrue(results.isSuccess(),"The service didn't find the person.");
    }
    @Test
    public void testFindPersonMultiplePass(){
        Result.PersonList results = null;
        try {
            String personID = "pers";
            String personID2 = "pers2";
            String username = "test1";
            Person firstPerson = new Person(personID,username,"Shad","Torrie",'M');
            Person secondPerson = new Person(personID2,username,"Shad","Torrie",'M');
            dao.insert(firstPerson);
            dao.insert(secondPerson);
            DAO authDao = new AuthDAO(db);
            String authID = "test1234";
            authDao.insert(new Auth(authID,username));
            db.closeConnection(true);
            results = (Result.PersonList) service.requestService(new Request.Person(authID));
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
        Result.Person results = null;
        try {
            String personID = "pers";
            dao.insert(new Person(personID,"test","Shad","Torrie",'M'));
            String authID = "test1234";
            String username = "test1";
            db.closeConnection(true);
            results = (Result.Person) service.requestService(new Request.Person(personID, authID));
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
        }
        assertNotNull(results,"Result was not received.");
        assertFalse(results.isSuccess(),"The authentication token should be invalid");
        assertEquals("Invalid auth token",results.getMessage(),"The error message was incorrect.");
    }
    @Test
    public void testFindPersonFail(){
        Result.Person results = null;
        try {
            DAO authDao = new AuthDAO(db);
            String authID = "test1234";
            String username = "test1";
            authDao.insert(new Auth(authID,username));
            db.closeConnection(true);
            results = (Result.Person) service.requestService(new Request.Person("test123", authID));
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
        }
        assertNotNull(results,"Result was not received.");
        assertFalse(results.isSuccess(),"The person should not be found");
        assertEquals("Invalid personID parameter",results.getMessage(),"The error message was incorrect.");
    }

    @AfterEach
    public void teardown() throws DataAccessException {
        super.teardown();
    }

}