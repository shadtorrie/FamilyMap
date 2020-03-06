package Services;

import DAOs.DAO;
import DAOs.PersonDAO;
import DAOs.UserDAO;
import Models.PersonModel;
import Models.UserModel;
import Request.FillRequest;
import Request.LoginRequest;
import Request.RegisterRequest;
import Request.Requests;
import Result.FillResult;
import Result.LoginResult;
import Result.RegisterResult;
import Result.Results;

import java.sql.SQLException;

public class RegisterS extends Service {
    /**
     *
     */
    public RegisterS() throws DataAccessException {
        super();
    }

    @Override
    public Results requestService(Requests request) {
        try{
            if(!request.getClass().equals(RegisterRequest.class)){
                throw new DataAccessException();
            }
            RegisterRequest registerRequest = (RegisterRequest) request;
            dbConnection.openConnection();
            DAO dao = new UserDAO(dbConnection);
            if(registerRequest.getUserName().length()==0||registerRequest.getPassword().length()==0
                    ||registerRequest.getEmail().length()==0||registerRequest.getFirstName().length()==0
                    ||registerRequest.getLastName().length()==0|| !registerRequest.getGender().equals("m") && !registerRequest.getGender().equals("f")){
                return new RegisterResult("Error: Request property missing or has invalid value",false);
            }

            UserModel result = (UserModel) dao.find(registerRequest.getUserName());
            if(result!= null ){
                dbConnection.closeConnection(false);
                return new RegisterResult("Error: Username already taken by another user",false);
            }

            UserModel userResultModel = (UserModel) dao.insert(new UserModel(registerRequest.getUserName(),registerRequest.getPassword(),registerRequest.getEmail()));
            if(userResultModel==null){
                dbConnection.closeConnection(false);
                return new RegisterResult("Error: Request property missing or has invalid value",false);
            }
            dbConnection.closeConnection(true);
            Service fillService = new FillS();
            FillResult fillResult = (FillResult) fillService.requestService(new FillRequest(userResultModel.getID(),4,registerRequest.getGender(),registerRequest.getFirstName(),registerRequest.getLastName()));
            if(!fillResult.isSuccess()){
                return new RegisterResult("Internal server error",false);
            }
            dbConnection.openConnection();
            PersonDAO personDao=new PersonDAO(dbConnection);
            PersonModel personResultModel = (PersonModel) personDao.find(userResultModel.getID(),registerRequest.getFirstName(),registerRequest.getLastName());
            if(personResultModel==null){
                dbConnection.closeConnection(false);
                return new RegisterResult("Internal server error",false);
            }
            dbConnection.closeConnection(true);
            Service loginService = new LoginS();
            LoginResult loginResult = (LoginResult) loginService.requestService(new LoginRequest(userResultModel.getID(),userResultModel.getPassword()));
            return new RegisterResult(loginResult.getAuthToken(),userResultModel.getID(),personResultModel.getID(),true);
        }
        catch(DataAccessException | SQLException e){
            e.printStackTrace();
            try {
                dbConnection.closeConnection(false);
            } catch (DataAccessException ex) {
                ex.printStackTrace();
            }
            return new RegisterResult("Internal server error",false);
        }
    }
}
