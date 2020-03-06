package Services;

import DAOs.DAO;
import DAOs.PersonDAO;
import ModelsServer.Model;
import Request.Person;
import Request.Requests;
import Result.PersonList;
import Result.Results;

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
        if(request.getClass()!= Person.class){
            return null;
        }
        Person personRequest = (Person) request;
        dbConnection = new Database();
        try {
            dbConnection.openConnection();
            String username= super.authenticate(personRequest.getAuthentication());
            if(username.equals("")){
                dbConnection.closeConnection(false);
                return new Result.Person("Error: Invalid auth token",false);
            }
            DAO personDao = new PersonDAO(dbConnection);
            Results result = null;
            if(personRequest.getPersonID()==null){
                ArrayList<Model> people = personDao.findMultiple(username);
                ArrayList<Result.Person> peopleResults = new ArrayList<>();
                for(Model i:people){
                    ModelsServer.Person j = (ModelsServer.Person)i;
                    ModelsServer.Person returnPerson = (ModelsServer.Person) personDao.find(j.getID());
                    peopleResults.add(new Result.Person(returnPerson.getAssociatedUsername(), returnPerson.getID(),returnPerson.getFirstName(),
                            returnPerson.getLastName(),returnPerson.getGender(),returnPerson.getFatherID(),returnPerson.getMotherID(),returnPerson.getSpouseID(),true));
                }
                result = new PersonList(peopleResults,true);
            }
            else{
                ModelsServer.Person returnModel = (ModelsServer.Person) personDao.find(personRequest.getPersonID());
                if(returnModel==null){
                    dbConnection.closeConnection(false);
                    return new Result.Person("Error: Invalid personID parameter",false);
                }
                ModelsServer.Person returnPerson = (ModelsServer.Person) personDao.find(returnModel.getID());
                if(!returnPerson.getAssociatedUsername().equals(username)){
                    dbConnection.closeConnection(false);
                    return new Result.Person("Requested person does not belong to this user",false);
                }
                result = new Result.Person(returnPerson.getAssociatedUsername(), returnPerson.getID(),returnPerson.getFirstName(),
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
            return new Result.Person("Internal server error",false);
        }
        
    }
}
