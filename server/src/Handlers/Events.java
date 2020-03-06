package Handlers;

import Request.Event;
import Result.Results;
import Services.DataAccessException;
import Services.EventsS;
import Services.Service;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Events extends Handler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        Results respData = null;
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                Headers reqHeaders = exchange.getRequestHeaders();
                String authToken = reqHeaders.getFirst("Authorization");
                Service service = new EventsS();
                String path=exchange.getRequestURI().getPath();
                String eventID=path.substring(path.lastIndexOf("event")+"event".length());
                if(eventID.length()>0){
                    respData = service.requestService(new Event(eventID.substring(1),authToken));
                    if(respData.isSuccess()) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    }
                    else{
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    }
                }
                else {
                    respData = service.requestService(new Event(authToken));
                    if(respData.isSuccess()) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    }
                    else{
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    }
                }
                success = true;
            }

            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }
        }
        catch (IOException | DataAccessException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            OutputStream respBody = exchange.getResponseBody();
            Gson gson = new Gson();
            respBody.write(gson.toJson(respData).getBytes(StandardCharsets.UTF_8));
            respBody.close();
            e.printStackTrace();
        }
        OutputStream respBody = exchange.getResponseBody();
        Gson gson = new Gson();
        respBody.write(gson.toJson(respData).getBytes(StandardCharsets.UTF_8));
        respBody.close();
    }
}
