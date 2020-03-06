package Services;

import DAOs.DAO;
import DAOs.EventDAO;
import DAOs.PersonDAO;
import DAOs.UserDAO;
import ModelsServer.Event;
import ModelsServer.Model;
import ModelsServer.Person;
import ModelsServer.User;
import Request.Load;
import Request.Requests;
import Result.Results;

import java.sql.SQLException;
import java.util.ArrayList;

public class LoadS extends Service{
    private ArrayList<User> users;
    private ArrayList<Person> persons;
    private ArrayList<Event> events;

    public LoadS(ArrayList<User> users, ArrayList<Person> persons, ArrayList<Event> events) throws DataAccessException {
        super();
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    /**
     *Load service constructor.
     */
    public LoadS() throws DataAccessException {
        super();
    }


    /**
     * Requests the Load Service
     * @param request
     * @return
     */
    @Override
    public Results requestService(Requests request) {
        if(request.getClass()!= Load.class){
            return null;
        }
        Load loadRequest = (Load) request;
        dbConnection = new Database();
        try {
            dbConnection.openConnection();
            DAO eventDao = new EventDAO(dbConnection);
            ArrayList<Event> events = loadRequest.getEvents();
            for(Event i :events) {
                eventDao.insert(i);
            }
            DAO personDao = new PersonDAO(dbConnection);
            ArrayList<Person> people = loadRequest.getPersons();
            for(Person i :people) {
                 personDao.insert(i);
            }
            DAO userDao = new UserDAO(dbConnection);
            ArrayList<User> users = loadRequest.getUsers();
            for(User i :users) {
                userDao.insert(i);
            }
            Results result = new Result.Load("Load successful",true);
            dbConnection.closeConnection(true);
            return result;
        } catch (DataAccessException | SQLException e) {
            try {
                dbConnection.closeConnection(false);
            } catch (DataAccessException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return new Result.Event("Event does not exist",false);
        }
    }

}
