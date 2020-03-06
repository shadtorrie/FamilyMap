package Services;

import DAOs.DAO;
import DAOs.EventDAO;
import DAOs.PersonDAO;
import DAOs.UserDAO;
import Data.*;
import ModelsServer.Event;
import ModelsServer.Person;
import Request.Fill;
import Request.Requests;
import Result.Results;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

public class FillS extends Service {
    private DAO eventDao;
    private DAO personDao;
    private String username;
    private fNames fnames;
    private mNames mnames;
    private sNames snames;
    private Locations locations;
    private int genCount;
    public FillS() throws DataAccessException {
        super();
    }

    @Override
    public Results requestService(Requests request) {
        if(request.getClass()!= Fill.class){
            return null;
        }
        Fill fillRequest = (Fill) request;
        eventDao = new EventDAO(dbConnection);
        personDao = new PersonDAO(dbConnection);
        DAO userDao = new UserDAO(dbConnection);
        try {
            dbConnection.openConnection();
            Results result = null;
            username = fillRequest.getUserName();
            genCount = fillRequest.getGenCount();
            if(userDao.find(username)==null||genCount<0){
                dbConnection.closeConnection(false);
                return new Result.Fill("Invalid username or generations parameter",false);
            }
            eventDao.deleteByUsername(username);
            personDao.deleteByUsername(username);
            grabInfo();
            generateFirstPerson(fillRequest);
            if(genCount!=0)
                result = new Result.Fill("Successfully added "  +getPersonCount() + " persons and " + getEventCount() + " events to the database.",true);
            else{
                result = new Result.Fill("Successfully added "  + 1 + " persons and " + 1 + " events to the database.",true);

            }
            dbConnection.closeConnection(true);
            return result;
        } catch (DataAccessException | IOException | SQLException e) {
            try {
                dbConnection.closeConnection(false);
            } catch (DataAccessException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return new Result.Fill(" Internal server error",false);
        }
    }

    private int getEventCount() {
        int eventCount = getPersonCount();
        eventCount *= 3;
        eventCount -= 2;
        return eventCount;
    }

    private int getPersonCount() {
        int personCount =0;
        for(int i =1;i<=genCount;++i){
            personCount+=Math.pow(2,i);
        }
        personCount+=1;
        return personCount;
    }

    private void grabInfo() throws IOException {
        Gson gson = new Gson();
        fnames = gson.fromJson(new String(Files.readAllBytes(Paths.get("json/fnames.json"))), fNames.class);
        mnames = gson.fromJson(new String(Files.readAllBytes(Paths.get("json/mnames.json"))), mNames.class);
        snames = gson.fromJson(new String(Files.readAllBytes(Paths.get("json/snames.json"))), sNames.class);
        locations = gson.fromJson(new String(Files.readAllBytes(Paths.get("json/locations.json"))), Locations.class);

    }

    private void generateFirstPerson(Fill request) throws DataAccessException, SQLException {
        Person person;
        if(request.getFirstName()==null){
            boolean male = isMale();
            if(male){
                person = createMale();
            }
            else {
                person = createFemale();
            }
        }
        else{
            person = new Person(UUID.randomUUID().toString(),username,request.getFirstName(),request.getLastName(),request.getGender());
        }
        Location location = locations.getRandom();
        Random random = new Random();
        Event event = new Event(UUID.randomUUID().toString(),person.getID(),username,location.latitude,location.longitude,
                location.country,location.city,"Birth", Calendar.getInstance().get(Calendar.YEAR)-random.nextInt(119));
        eventDao.insert(event);
        if(genCount>0){
            createParents(person,event.getYear(),genCount);
        }
        personDao.insert(person);
    }

    private void createParents(Person child, int birthOfChild, int currentGenCount) throws DataAccessException, SQLException {
        Person mom = createFemale();
        Person dad = createMale(child.getLastName());
        mom.setSpouseID(dad.getID());
        dad.setSpouseID(mom.getID());
        child.setFatherID(dad.getID());
        child.setMotherID(mom.getID());
        createEvents(mom,dad,birthOfChild,currentGenCount);
    }

    private void createEvents(Person mom, Person dad, int birthOfChild, int currentGenCount) throws DataAccessException, SQLException {
        Random random = new Random();
        //Births
        Location momBirth =locations.getRandom();
        Event momBirthEvent = new Event(UUID.randomUUID().toString(),mom.getID(),username,momBirth.latitude,momBirth.longitude,
                momBirth.country,momBirth.city,"Birth", birthOfChild-13-random.nextInt(37));
        Location dadBirth =locations.getRandom();
        Event dadBirthEvent = new Event(UUID.randomUUID().toString(),dad.getID(),username,dadBirth.latitude,dadBirth.longitude,
                dadBirth.country,dadBirth.city,"Birth", birthOfChild-13-random.nextInt(106));
        eventDao.insert(momBirthEvent);
        eventDao.insert(dadBirthEvent);
        //Marriage
        Location marriageLocation =locations.getRandom();
        Event dadMarriage = new Event(UUID.randomUUID().toString(),dad.getID(),username,marriageLocation.latitude,marriageLocation.longitude,
                marriageLocation.country,marriageLocation.city,"Marriage", birthOfChild);
        Event momMarriage = new Event(UUID.randomUUID().toString(),mom.getID(),username,marriageLocation.latitude,marriageLocation.longitude,
                marriageLocation.country,marriageLocation.city,"Marriage", birthOfChild);
        eventDao.insert(dadMarriage);
        eventDao.insert(momMarriage);
        //Death
        Location dadDeathLocation = locations.getRandom();
        Location momDeathLocation = locations.getRandom();
        int dadDeathDate = birthOfChild+random.nextInt(dadBirthEvent.getYear()-birthOfChild+120);
        Event dadDeath = new Event(UUID.randomUUID().toString(),dad.getID(),username,dadDeathLocation.latitude,dadDeathLocation.longitude,
                dadDeathLocation.country,dadDeathLocation.city,"Death", dadDeathDate);
        int momDeathDate = birthOfChild+random.nextInt(momBirthEvent.getYear()-birthOfChild+120);
        Event momDeath = new Event(UUID.randomUUID().toString(),mom.getID(),username,momDeathLocation.latitude,momDeathLocation.longitude,
                momDeathLocation.country,momDeathLocation.city,"Death", momDeathDate);
        eventDao.insert(dadDeath);
        eventDao.insert(momDeath);
        if(currentGenCount>1){
            createParents(mom,momBirthEvent.getYear(),currentGenCount-1);
            createParents(dad,dadBirthEvent.getYear(),currentGenCount-1);
        }
        personDao.insert(dad);
        personDao.insert(mom);
    }

    private Person createFemale() {
        FirstName firstName = fnames;
        return new Person(UUID.randomUUID().toString(),username,firstName.getRandom(),snames.getRandom(),"f");
    }

    private Person createMale(String last_name) {
        FirstName firstName = mnames;
        return new Person(UUID.randomUUID().toString(),username,firstName.getRandom(),last_name,"m");
    }
    private Person createMale() {
        FirstName firstName = mnames;
        return new Person(UUID.randomUUID().toString(),username,firstName.getRandom(),snames.getRandom(),"m");
    }

    private boolean isMale() {
        Random random = new Random();
        int genderInt = random.nextInt(1);
        return genderInt==0;
    }
}
