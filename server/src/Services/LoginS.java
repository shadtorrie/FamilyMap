package Services;

import DAOs.AuthDAO;
import DAOs.DAO;
import DAOs.UserDAO;
import Models.AuthModel;
import Models.UserModel;
import Requests.LoginRequest;
import Requests.Requests;
import Results.LoginResult;
import Results.Results;

import java.sql.SQLException;
import java.util.UUID;

public class LoginS extends Service {
    /**
     *
     */
    public LoginS() throws DataAccessException {
        super();
    }

    /**
     *
     * @param request
     * @return
     */
    @Override
    public Results requestService(Requests request) {
        try{
            if(!request.getClass().equals(LoginRequest.class)){
                throw new DataAccessException();
            }
            LoginRequest loginRequest = (LoginRequest) request;
            dbConnection.openConnection();
            DAO dao = new UserDAO(dbConnection);
            UserModel resultModel = (UserModel) ((UserDAO)dao).findByUsernameAndPassword(loginRequest.getUserName(),loginRequest.getPassword());
            if(resultModel==null){
                dbConnection.closeConnection(false);
                return new LoginResult("Error: Invalid Username and/or password",false);
            }
            dao = new AuthDAO(dbConnection);
            AuthModel authModel = (AuthModel) dao.insert(new AuthModel(UUID.randomUUID().toString(),resultModel.getID()));
            dbConnection.closeConnection(true);
            return new LoginResult(authModel.getID(),resultModel.getID(),resultModel.getPersonID(),true);
        }
        catch(DataAccessException | SQLException e){
            e.printStackTrace();
            try {
                dbConnection.closeConnection(false);
            } catch (DataAccessException ex) {
                ex.printStackTrace();
            }
            return new LoginResult("Internal server error",false);
        }
    }

}
