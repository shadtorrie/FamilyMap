package Handlers;

import Result.Results;
import Services.*;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class Register extends Handler  {
    static class RegisterBody {
        String userName = null;
        String password = null;
        String email = null;
        String firstName = null;
        String lastName = null;
        String gender;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        Results respData = null;
        Gson gson = new Gson();
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                InputStream requestBody = exchange.getRequestBody();
                RegisterBody registerBody = gson.fromJson(new InputStreamReader(requestBody),RegisterBody.class);
                if(registerBody.userName!=null&&registerBody.password!=null
                        &&registerBody.email!=null
                        &&registerBody.firstName!=null&&registerBody.lastName!=null
                        &&registerBody.gender!=null) {
                    Service service = new RegisterS();
                    respData = service.requestService(new Request.Register(registerBody.userName,
                            registerBody.password, registerBody.email,
                            registerBody.firstName, registerBody.lastName,
                            registerBody.gender));
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
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
