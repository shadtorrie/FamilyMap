package Handlers;

import Request.ClearRequest;
import Request.LoadRequest;
import Result.ClearResult;
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
import java.nio.charset.StandardCharsets;

public class LoadHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        Results resultData = null;
        Gson gson = new Gson();
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                Headers reqHeaders = exchange.getRequestHeaders();
                Service service = new ClearS();
                Results respData = service.requestService(new ClearRequest());
                if(!((ClearResult)respData).isSuccess()){
                    success=false;
                }
                else{
                    InputStream reqBody = exchange.getRequestBody();
                    String request = super.readString(reqBody);
                    LoadRequest requestLoad = gson.fromJson(request, LoadRequest.class);
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
