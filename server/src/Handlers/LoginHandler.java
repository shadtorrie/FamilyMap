package Handlers;

import Request.LoginRequest;
import Result.LoginResult;
import Services.*;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class LoginHandler extends Handler  {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        LoginResult respData = null;
        Gson gson = new Gson();
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                InputStream requestBody = exchange.getRequestBody();
                 LoginRequest loginBody = gson.fromJson(new InputStreamReader(requestBody), LoginRequest.class);
                if(loginBody.getUserName()!=null&&loginBody.getPassword()!=null) {
                    Service service = new LoginS();
                    respData = (LoginResult) service.requestService(new LoginRequest(loginBody.getUserName(),loginBody.getPassword()));
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
                    exchange.getResponseBody().close();
                }
        }
        catch (IOException | DataAccessException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
        OutputStream respBody = exchange.getResponseBody();
        respBody.write(gson.toJson(respData).getBytes(StandardCharsets.UTF_8));
        respBody.close();
    }
}
