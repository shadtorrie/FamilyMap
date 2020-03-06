package Services;

import DAOs.AuthDAO;
import DAOs.DAO;
import Models.AuthModel;
import Request.Requests;
import Result.Results;

public abstract class Service {
    protected Database dbConnection;
    public Service() throws DataAccessException {
        this.dbConnection = new Database();
        dbConnection.getConnection();
    }

    public abstract Results requestService(Requests request) throws DataAccessException;

    protected String authenticate(String authentication) throws DataAccessException {
        DAO authenticateDAO = new AuthDAO(dbConnection);
        AuthModel auth = (AuthModel) authenticateDAO.find(authentication);
        if(auth!=null){
            return auth.getUserName();
        }
        return "";
    }
}
