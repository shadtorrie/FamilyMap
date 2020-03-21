package Services;

import DAOs.*;
import Requests.ClearRequest;
import Requests.Requests;
import Results.ClearResult;
import Results.Results;

public class ClearS extends Service{
    /**
     *
     * @param request
     * @return
     */
    @Override
    public Results requestService(Requests request) {
        if(request.getClass()!= ClearRequest.class){
            return null;
        }
        dbConnection = new Database();
        try {
            dbConnection.openConnection();
            DAO eventDao = new EventDAO(dbConnection);
            DAO personDao = new PersonDAO(dbConnection);
            DAO userDao = new UserDAO(dbConnection);
            DAO authDao = new AuthDAO(dbConnection);
            eventDao.clear();
            personDao.clear();
            userDao.clear();
            authDao.clear();
            dbConnection.closeConnection(true);
            return new ClearResult("Clear succeeded.",true);
        } catch (DataAccessException e) {
            try {
                dbConnection.closeConnection(false);
            } catch (DataAccessException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return new ClearResult("Internal server error",false);
        }
    }

    /**
     *
     */
    public ClearS() throws DataAccessException {
        super();
    }
}
