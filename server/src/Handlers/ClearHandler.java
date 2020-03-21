package Handlers;

import Requests.ClearRequest;
import Results.Results;
import Services.DataAccessException;
import Services.ClearS;
import Services.Service;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class ClearHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        Results respData = null;
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                Headers reqHeaders = exchange.getRequestHeaders();
                Service service = new ClearS();
                respData = service.requestService(new ClearRequest());
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                success = true;
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
        Gson gson = new Gson();
        respBody.write(gson.toJson(respData).getBytes(StandardCharsets.UTF_8));
        respBody.close();
    }
}
