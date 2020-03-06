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
        char gender=0;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                InputStream requestBody = exchange.getRequestBody();
                Gson gson = new Gson();
                RegisterBody registerBody = gson.fromJson(new InputStreamReader(requestBody),RegisterBody.class);
                if(registerBody.userName!=null&&registerBody.password!=null
                        &&registerBody.email!=null
                        &&registerBody.firstName!=null&&registerBody.lastName!=null
                        &&registerBody.gender!=0) {
                    Service service = new RegisterS();
                    Results respData = service.requestService(new Request.Register(registerBody.userName,
                            registerBody.password, registerBody.email,
                            registerBody.firstName, registerBody.lastName,
                            registerBody.gender));
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    respBody.write(gson.toJson(respData).getBytes(StandardCharsets.UTF_8));
                    respBody.close();
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
    }
}
