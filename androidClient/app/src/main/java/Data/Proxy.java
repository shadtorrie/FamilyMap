package Data;

import android.annotation.TargetApi;
import android.os.Build;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import Data.Exceptions.RequestFailedException;
import Models.AuthModel;
import Models.EventModel;
import Models.PersonModel;
import Requests.EventRequest;
import Requests.LoginRequest;
import Requests.PersonRequest;
import Requests.RegisterRequest;
import Results.EventListResult;
import Results.EventResult;
import Results.LoginResult;
import Results.PersonList;
import Results.PersonResult;
import Results.RegisterResult;

public class Proxy {
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void request(LoginRequest request, URL url) throws RequestFailedException, IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        OutputStream outputStream = connection.getOutputStream();
        Gson gson = new Gson();
        outputStream.write(gson.toJson(request).getBytes(StandardCharsets.UTF_8));
        connection.connect();
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            // Get response body input stream
            InputStream responseBody = connection.getInputStream();
            String requestString = readString(responseBody);
            LoginResult loginResult = gson.fromJson(requestString, LoginResult.class);
            if(loginResult.isSuccess()){
                AuthModel authModel = new AuthModel(loginResult.getAuthToken(),loginResult.getUserName());
                ModelData.setFirstPerson(new PersonModel(loginResult.getPersonID()));
                ModelData.setAuthentication(authModel);
            }
            else {
                throw new RequestFailedException(loginResult.getMessage());
            }
        }
        else{
            InputStream responseBody = connection.getErrorStream();
            String requestString = readString(responseBody);
            LoginResult loginResult = gson.fromJson(requestString, LoginResult.class);
            throw new RequestFailedException(loginResult.getMessage());
        }
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void request(RegisterRequest request, URL url) throws RequestFailedException, IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        OutputStream outputStream = connection.getOutputStream();
        Gson gson = new Gson();
        outputStream.write(gson.toJson(request).getBytes(StandardCharsets.UTF_8));
        connection.connect();
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            // Get response body input stream
            InputStream responseBody = connection.getInputStream();
            String requestString = readString(responseBody);
            RegisterResult registerResult = gson.fromJson(requestString, RegisterResult.class);
            if(registerResult.isSuccess()){
                AuthModel authModel = new AuthModel(registerResult.getAuthToken(),registerResult.getUsername());
                ModelData.setFirstPerson(new PersonModel(registerResult.getPersonID()));
                ModelData.setAuthentication(authModel);
            }
            else {
                throw new RequestFailedException(registerResult.getMessage());
            }
        }
        else{
            InputStream responseBody = connection.getErrorStream();
            String requestString = readString(responseBody);
            LoginResult loginResult = gson.fromJson(requestString, LoginResult.class);
            throw new RequestFailedException(loginResult.getMessage());
        }
    }
    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void request(PersonRequest request, URL url) throws  IOException, RequestFailedException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        Gson gson = new Gson();
        connection.setRequestProperty("Authorization",request.getAuthentication());
        connection.connect();
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            // Get response body input stream
            InputStream responseBody = connection.getInputStream();
            String requestString = readString(responseBody);
            PersonList personList = gson.fromJson(requestString, PersonList.class);
            if (personList.isSuccess()) {
                ArrayList<PersonResult> people = personList.getData();
                String firstPerson = ModelData.getFirstPerson().getID();
                if(people.size()==0){
                    throw new RequestFailedException("No events found");
                }
                for(PersonResult i : people){
                    if(i.getPersonID().equals(firstPerson)){
                        ModelData.setFirstPerson(new PersonModel(i.getPersonID(),
                                i.getAssociatedUsername(),i.getFirstName(),i.getLastName(),
                                i.getGender(),i.getFatherID(),i.getMotherID(),i.getSpouseID()));
                    }
                    else{
                        ModelData.insertPerson(new PersonModel(i.getPersonID(),
                                i.getAssociatedUsername(),i.getFirstName(),i.getLastName(),
                                i.getGender(),i.getFatherID(),i.getMotherID(),i.getSpouseID()));
                    }
                }
            } else {
                throw new RequestFailedException(personList.getData().get(0).getMessage());
            }
        }
        else{
            InputStream responseBody = connection.getErrorStream();
            String requestString = readString(responseBody);
            LoginResult loginResult = gson.fromJson(requestString, LoginResult.class);
            throw new RequestFailedException(loginResult.getMessage());
        }
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void request(EventRequest request, URL url) throws  IOException, RequestFailedException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        Gson gson = new Gson();
        connection.setRequestProperty("Authorization",request.getAuthentication());
        connection.connect();
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            // Get response body input stream
            InputStream responseBody = connection.getInputStream();
            String requestString = readString(responseBody);
            EventListResult eventList = gson.fromJson(requestString, EventListResult.class);
            if (eventList.isSuccess()) {
                ArrayList<EventResult> events = eventList.getData();
                if(events.size()==0){
                    throw new RequestFailedException("No events found");
                }
                for(EventResult i : events){
                    ModelData.insertEvent(new EventModel(i.getEventID(),i.getPersonID(),
                            i.getAssociatedUsername(),i.getLatitude(),i.getLongitude(),
                            i.getCountry(),i.getCity(),i.getEventType(),i.getYear()));
                }
            } else {
                throw new RequestFailedException(eventList.getData().get(0).getMessage());
            }
        }
        else{
            InputStream responseBody = connection.getErrorStream();
            String requestString = readString(responseBody);
            LoginResult loginResult = gson.fromJson(requestString, LoginResult.class);
            throw new RequestFailedException(loginResult.getMessage());
        }
    }
}
