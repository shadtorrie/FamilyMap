package Tests.ServiceTests;

import DAOs.AuthDAO;
import DAOs.PersonDAO;
import DAOs.UserDAO;
import ModelsServer.Auth;
import ModelsServer.Model;
import ModelsServer.Person;
import ModelsServer.User;
import Request.Login;
import Services.DataAccessException;
import Services.LoginS;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LoginSTest extends ServiceTest {

    @BeforeEach
    void setUp() throws DataAccessException {
        super.setup();
        service= new LoginS();
        dao = new UserDAO(db);
    }
    @Test
    public void testLoginPass(){
        Result.Login result=null;
        User user= null;
        Person person = null;
        try{
            user = new User("shad","testing123","test@gmail.com");
            dao.insert(user);
            dao=new PersonDAO(db);
            person = new Person("testID",user.getID(),"shad","Torrie",'m');
            dao.insert(person);
            db.closeConnection(true);
            result = (Result.Login) service.requestService(new Login(user.getID(),user.getPassword()));
        }
        catch(SQLException | DataAccessException e){
            e.printStackTrace();
        }
        assertNotNull(result,"Login in service failed.");
        assertTrue(result.isSuccess(),"Login failed");
        assertEquals(user.getID(),result.getUserName(),"Username not saved correctly");
        assertEquals(person.getID(),result.getPersonID(),"Person_ID was not saved correctly");

        ArrayList<Model> auths=null;
        try{
            db.openConnection();
            dao=new AuthDAO(db);
            auths = dao.findMultiple(user.getID());

        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        assertNotNull(auths,"Getting auth token failed.");
        assertEquals(1,auths.size(),"Incorrect amount of auth tokens");
        assertEquals(((Auth)auths.get(0)).getID(),result.getAuthToken(),"Auth token not saved correctly.");
    }
    @Test
    public void testLoginFail(){
        Result.Login result=null;
        User user= null;
        Person person = null;
        try{
            user = new User("shad","testing123","test@gmail.com");
            dao=new PersonDAO(db);
            person = new Person("testID",user.getID(),"shad","Torrie",'m');
            dao.insert(person);
            db.closeConnection(true);
            result = (Result.Login) service.requestService(new Login(user.getID(),user.getPassword()));
        }
        catch(SQLException | DataAccessException e){
            e.printStackTrace();
        }
        assertNotNull(result,"Login in service failed.");
        assertFalse(result.isSuccess(),"Login should have failed");
        assertEquals("Error: Request property missing or has invalid value",result.getMessage(),"Incorrect Message");

        ArrayList<Model> auths=null;
        try{
            db.openConnection();
            dao=new AuthDAO(db);
            auths = dao.findMultiple(user.getID());

        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        assertNotNull(auths,"Getting auth token failed.");
        assertEquals(0,auths.size(),"Incorrect amount of auth tokens, there should be no auth token for this user.");
    }
    @AfterEach
    void tearDown() throws DataAccessException {
        super.teardown();
    }
}