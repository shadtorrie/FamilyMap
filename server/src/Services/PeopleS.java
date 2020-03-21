package Services;

import DAOs.DAO;
import DAOs.PersonDAO;
import Models.Model;
import Models.PersonModel;
import Requests.PersonRequest;
import Requests.Requests;
import Results.PersonList;
import Results.PersonResult;
import Results.Results;

import java.util.ArrayList;

public class PeopleS extends Service {
    /**
     *
     */
    public PeopleS() throws DataAccessException {
        super();
    }

    /**
     *
     * @param request
     * @return
     */
    @Override
    public Results requestService(Requests request) {
        if(request.getClass()!= PersonRequest.class){
            return null;
        }
        PersonRequest personRequest = (PersonRequest) request;
        dbConnection = new Database();
        try {
            dbConnection.openConnection();
            String username= super.authenticate(personRequest.getAuthentication());
            if(username.equals("")){
                dbConnection.closeConnection(false);
                return new PersonResult("Error: Invalid auth token",false);
            }
            DAO personDao = new PersonDAO(dbConnection);
            Results result = null;
            if(personRequest.getPersonID()==null){
                ArrayList<Model> people = personDao.findMultiple(username);
                ArrayList<PersonResult> peopleResults = new ArrayList<>();
                for(Model i:people){
                    PersonModel j = (PersonModel)i;
                    PersonModel returnPerson = (PersonModel) personDao.find(j.getID());
                    peopleResults.add(new PersonResult(returnPerson.getAssociatedUsername(), returnPerson.getID(),returnPerson.getFirstName(),
                            returnPerson.getLastName(),returnPerson.getGender(),returnPerson.getFatherID(),returnPerson.getMotherID(),returnPerson.getSpouseID(),true));
                }
                result = new PersonList(peopleResults,true);
            }
            else{
                PersonModel returnModel = (PersonModel) personDao.find(personRequest.getPersonID());
                if(returnModel==null){
                    dbConnection.closeConnection(false);
                    return new PersonResult("Error: Invalid personID parameter",false);
                }
                PersonModel returnPerson = (PersonModel) personDao.find(returnModel.getID());
                if(!returnPerson.getAssociatedUsername().equals(username)){
                    dbConnection.closeConnection(false);
                    return new PersonResult("Error: Requested person does not belong to this user",false);
                }
                result = new PersonResult(returnPerson.getAssociatedUsername(), returnPerson.getID(),returnPerson.getFirstName(),
                        returnPerson.getLastName(),returnPerson.getGender(),returnPerson.getFatherID(),
                        returnPerson.getMotherID(),returnPerson.getSpouseID(),true);
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
            return new PersonResult("Internal server error",false);
        }
        
    }
}
