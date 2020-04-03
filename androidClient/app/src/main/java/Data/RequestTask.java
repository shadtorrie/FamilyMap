package Data;

import android.os.AsyncTask;

import java.net.URL;

import Requests.*;

public class RequestTask extends AsyncTask<URL,Integer,String> {
    private Requests request;
    private Proxy proxy;
    private Listener listener;
    public interface Listener {
        void onError(Error e);
        void onPostExecute(String message);
    }
    public RequestTask(Requests request,Listener listener) {
        this.request = request;
        this.proxy = new Proxy();
        this.listener = listener;
    }

    @Override
    protected String doInBackground(URL... urls) {
        StringBuilder returnString = new StringBuilder();
        PersonRequest personRequest = null;
        try {
            for (URL i : urls) {
                if (i.getPath().contains("login")) {
                    proxy.request((LoginRequest) request, i);
                    returnString.append("Sign in successful for: ");
                }
                else if (i.getPath().contains("register")) {
                    proxy.request((RegisterRequest) request, i);
                    returnString.append( "Registration successful for: ");
                }
                else if(i.getPath().contains("person")){
                    personRequest = new PersonRequest(ModelData.getAuthentication().getID());
                    proxy.request(personRequest,i);
                    returnString.append(ModelData.getFirstPerson().getFirstName())
                            .append(" ").append(ModelData.getFirstPerson().getLastName());
                }
                else if(i.getPath().contains("event")){
                    EventRequest eventRequest= new EventRequest(ModelData.getAuthentication().getID());
                    proxy.request(eventRequest,i);
                }
            }
        }
        catch(Exception e){
            return e.getMessage();
        }
        return returnString.toString();
    }
    protected void onPostExecute(String result) {
        listener.onPostExecute(result);
    }
}
