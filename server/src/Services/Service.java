package Services;

import Request.Requests;
import Result.Results;

public abstract class Service {
    private Database dbConnection;
    public Service() throws DataAccessException {
        this.dbConnection = new Database();
        dbConnection.getConnection();
    }

    public abstract Results requestService(Requests request);
}
