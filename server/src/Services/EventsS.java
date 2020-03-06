package Services;

import DAOs.DAO;
import DAOs.EventDAO;
import DAOs.PersonDAO;
import ModelsServer.Model;
import ModelsServer.Person;
import Request.Event;
import Request.Requests;
import Result.EventList;
import Result.Results;

import java.util.ArrayList;

public class EventsS extends Service {
    /**
     *
     */
    public EventsS() throws DataAccessException {
        super();
    }

    /**
     *
     * @param request
     * @return
     */
    @Override
    public Results requestService(Requests request) {
        if(request.getClass()!= Event.class){
            return null;
        }
        Event eventRequest = (Event) request;
        dbConnection = new Database();
        try {
            dbConnection.openConnection();
            String username = super.authenticate(eventRequest.getAuthentication());
            if(username.equals("")){
                dbConnection.closeConnection(false);
                return new Result.Event("Error: Invalid auth token",false);
            }
            DAO eventDao = new EventDAO(dbConnection);
            Results result = null;
            if(eventRequest.getEventID()==null){
                ArrayList<Model> events = eventDao.findMultiple(username);
                ArrayList<Result.Event> eventResults = new ArrayList<>();
                DAO personDao = new PersonDAO(dbConnection);
                for(Model i:events){
                    ModelsServer.Event j = (ModelsServer.Event)i;
                    Person returnPerson = (Person) personDao.find(j.getPersonID());
                    eventResults.add( new Result.Event(returnPerson.getAssociatedUsername(),j.getID(),j.getPersonID(),
                            j.getLatitude(),j.getLongitude(),j.getCountry(),j.getCity(),
                            j.getEventType(),j.getYear(),true));
                }
                result = new EventList(eventResults,true);
            }
            else{
                ModelsServer.Event returnModel = (ModelsServer.Event) eventDao.find(eventRequest.getEventID());
                if(returnModel==null){
                    dbConnection.closeConnection(false);
                    return new Result.Event("Invalid eventID parameter",false);
                }
                DAO personDao = new PersonDAO(dbConnection);
                Person returnPerson = (Person) personDao.find(returnModel.getPersonID());
                if(!returnPerson.getAssociatedUsername().equals(username)){
                    dbConnection.closeConnection(false);
                    return new Result.Event("Requested event does not belong to this user",false);
                }
                result = new Result.Event(returnPerson.getAssociatedUsername(),returnModel.getID(),returnModel.getPersonID(),
                        returnModel.getLatitude(),returnModel.getLongitude(),returnModel.getCountry(),returnModel.getCity(),
                        returnModel.getEventType(),returnModel.getYear(),true);
            }


            dbConnection.closeConnection(true);
            return result;
        } catch (DataAccessException e) {
            try {
                dbConnection.closeConnection(false);
            } catch (DataAccessException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return new Result.Event("Internal server error",false);
        }
    }
}
