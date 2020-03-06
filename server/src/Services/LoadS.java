package Services;

import DAOs.DAO;
import DAOs.EventDAO;
import DAOs.PersonDAO;
import DAOs.UserDAO;
import Models.EventModel;
import Models.PersonModel;
import Models.UserModel;
import Requests.LoadRequest;
import Requests.Requests;
import Results.EventResult;
import Results.LoadResult;
import Results.Results;

import java.sql.SQLException;
import java.util.ArrayList;

public class LoadS extends Service{
    private ArrayList<UserModel> users;
    private ArrayList<PersonModel> persons;
    private ArrayList<EventModel> events;

    public LoadS(ArrayList<UserModel> users, ArrayList<PersonModel> persons, ArrayList<EventModel> events) throws DataAccessException {
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
        if(request.getClass()!= LoadRequest.class){
            return null;
        }
        LoadRequest loadRequest = (LoadRequest) request;
        dbConnection = new Database();
        try {
            dbConnection.openConnection();
            DAO eventDao = new EventDAO(dbConnection);
            ArrayList<EventModel> events = loadRequest.getEvents();
            for(EventModel i :events) {
                eventDao.insert(i);
            }
            DAO personDao = new PersonDAO(dbConnection);
            ArrayList<PersonModel> people = loadRequest.getPersons();
            for(PersonModel i :people) {
                 personDao.insert(i);
            }
            DAO userDao = new UserDAO(dbConnection);
            ArrayList<UserModel> users = loadRequest.getUsers();
            for(UserModel i :users) {
                userDao.insert(i);
            }
            Results result = new LoadResult("Successfully added "+users.size()+" users, "+people.size()+" persons, and "+events.size()+" events to the database.",true);
            dbConnection.closeConnection(true);
            return result;
        } catch (DataAccessException | SQLException e) {
            try {
                dbConnection.closeConnection(false);
            } catch (DataAccessException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return new EventResult("Event does not exist",false);
        }
    }

}
