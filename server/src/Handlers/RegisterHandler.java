package Handlers;

import Requests.RegisterRequest;
import Results.Results;
import Services.*;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class RegisterHandler extends Handler  {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        Results respData = null;
        Gson gson = new Gson();
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                InputStream requestBody = exchange.getRequestBody();
                RegisterRequest registerBody = gson.fromJson(new InputStreamReader(requestBody), RegisterRequest.class);
                if(registerBody.getUserName()!=null&&registerBody.getPassword()!=null
                        &&registerBody.getEmail()!=null
                        &&registerBody.getFirstName()!=null&&registerBody.getLastName()!=null
                        &&registerBody.getGender()!=null) {
                    Service service = new RegisterS();
                    respData = service.requestService(new RegisterRequest(registerBody.getUserName(),
                            registerBody.getPassword(), registerBody.getEmail(),
                            registerBody.getFirstName(), registerBody.getLastName(),
                            registerBody.getGender()));
                    if(respData.isSuccess()){
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    }
                    else{
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    }
                    success = true;
                }
            }
            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }
        }
        catch (IOException | DataAccessException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            e.printStackTrace();
        }
        OutputStream respBody = exchange.getResponseBody();
        respBody.write(gson.toJson(respData).getBytes(StandardCharsets.UTF_8));
        respBody.close();
    }
}
