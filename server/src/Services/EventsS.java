package Services;

import DAOs.DAO;
import DAOs.EventDAO;
import DAOs.PersonDAO;
import Models.EventModel;
import Models.Model;
import Models.PersonModel;
import Requests.EventRequest;
import Requests.Requests;
import Results.EventListResult;
import Results.EventResult;
import Results.Results;

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
        if(request.getClass()!= EventRequest.class){
            return null;
        }
        EventRequest eventRequest = (EventRequest) request;
        dbConnection = new Database();
        try {
            dbConnection.openConnection();
            String username = super.authenticate(eventRequest.getAuthentication());
            if(username.equals("")){
                dbConnection.closeConnection(false);
                return new EventResult("Error: Invalid auth token",false);
            }
            DAO eventDao = new EventDAO(dbConnection);
            Results result = null;
            if(eventRequest.getEventID()==null){
                ArrayList<Model> events = eventDao.findMultiple(username);
                ArrayList<EventResult> eventResults = new ArrayList<>();
                DAO personDao = new PersonDAO(dbConnection);
                for(Model i:events){
                    EventModel j = (EventModel)i;
                    PersonModel returnPerson = (PersonModel) personDao.find(j.getPersonID());
                    eventResults.add( new EventResult(returnPerson.getAssociatedUsername(),j.getID(),j.getPersonID(),
                            j.getLatitude(),j.getLongitude(),j.getCountry(),j.getCity(),
                            j.getEventType(),j.getYear(),true));
                }
                result = new EventListResult(eventResults,true);
            }
            else{
                EventModel returnModel = (EventModel) eventDao.find(eventRequest.getEventID());
                if(returnModel==null){
                    dbConnection.closeConnection(false);
                    return new EventResult("Error: Invalid eventID parameter",false);
                }
                DAO personDao = new PersonDAO(dbConnection);
                PersonModel returnPerson = (PersonModel) personDao.find(returnModel.getPersonID());
                if(!returnPerson.getAssociatedUsername().equals(username)){
                    dbConnection.closeConnection(false);
                    return new EventResult("Error: Requested event does not belong to this user",false);
                }
                result = new EventResult(returnPerson.getAssociatedUsername(),returnModel.getID(),returnModel.getPersonID(),
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
            return new EventResult("Internal server error",false);
        }
    }
}
