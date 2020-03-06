package Handlers;

import Request.Event;
import Result.Results;
import Services.DataAccessException;
import Services.EventsS;
import Services.FillS;
import Services.Service;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class Fill extends Handler  {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                Service service = new FillS();
                String path=exchange.getRequestURI().getPath();
                String username= path.substring(path.indexOf('/',1)+1,path.lastIndexOf('/'));
                String genCountString = path.substring(path.lastIndexOf('/')+1);
                int genCount =4;
                if(genCountString !=""){
                    genCount= Integer.parseInt(genCountString);
                }
                Results respData = null;
                respData = service.requestService(new Request.Fill(username,genCount));
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
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
