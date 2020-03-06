package Handlers;

import Request.Clear;
import Request.Requests;
import Result.Results;
import Services.ClearS;
import Services.DataAccessException;
import Services.LoadS;
import Services.Service;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Load extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        Results resultData = null;
        Gson gson = new Gson();
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                Headers reqHeaders = exchange.getRequestHeaders();
                Service service = new ClearS();
                Results respData = service.requestService(new Request.Clear());
                if(!((Result.Clear)respData).isSuccess()){
                    success=false;
                }
                else{
                    InputStream reqBody = exchange.getRequestBody();
                    String request = super.readString(reqBody);
                    Request.Load requestLoad = gson.fromJson(request, Request.Load.class);
                    LoadS loadService = new LoadS();
                    resultData = loadService.requestService(requestLoad);
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
        respBody.write(gson.toJson(resultData).getBytes(StandardCharsets.UTF_8));
        respBody.close();
    }
}
