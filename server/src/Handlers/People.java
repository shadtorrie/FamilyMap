package Handlers;

import Request.Event;
import Request.Person;
import Result.Results;
import Services.DataAccessException;
import Services.EventsS;
import Services.PeopleS;
import Services.Service;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class People extends Handler  {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                Headers reqHeaders = exchange.getRequestHeaders();
                String authToken = reqHeaders.getFirst("Authorization");
                Service service = new PeopleS();
                String path=exchange.getRequestURI().getPath();
                String personID=path.substring(path.lastIndexOf("person")+"person".length());
                Results respData = null;
                if(personID.length()>0){
                    respData = service.requestService(new Person(personID,authToken));
                }
                else {
                    respData = service.requestService(new Person(authToken));
                }
                if(((Result.PersonList)respData).isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else{
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
                OutputStream respBody = exchange.getResponseBody();
                Gson gson = new Gson();
                respBody.write(gson.toJson(respData).getBytes(StandardCharsets.UTF_8));
                respBody.close();
                success = true;
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
