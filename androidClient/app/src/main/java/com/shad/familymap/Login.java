package com.shad.familymap;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

import Data.RequestTask;
import Requests.*;


public class Login extends Fragment implements RequestTask.Listener {
    private String title;
    public static final String ARG_TITLE = "title";
    private EditText server_host;
    private EditText server_port;
    private EditText username;
    private EditText password;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private Button mLoginButton;
    private Button mRegisterButton;
    private RadioGroup mRadioGroupGender;
    private String gender ="m";
    public Login() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        server_host = v.findViewById(R.id.serverHost);
        server_port = v.findViewById(R.id.serverPort);
        username = v.findViewById(R.id.username);
        password= v.findViewById(R.id.password);
        firstName = v.findViewById(R.id.firstName);
        lastName= v.findViewById(R.id.lastName);
        email= v.findViewById(R.id.email);
        mRadioGroupGender = v.findViewById(R.id.radio_group);
        mRadioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radio_female:
                        gender = "f";
                        break;
                    case R.id.radio_male:
                        gender = "m";
                        break;
                }
            }
        });
        mLoginButton = v.findViewById((R.id.button_login));
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LoginRequest loginRequest = getLoginRequest();
                request(loginRequest);
                //Toast.makeText(getActivity(), loginRequest.getUserName() + " signed in", Toast.LENGTH_SHORT).show();
            }
        });
        mRegisterButton= v.findViewById((R.id.button_register));
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                RegisterRequest registerRequest = getRegisterRequest();
                request(registerRequest);
                //Toast.makeText(getActivity(), registerRequest.getUserName() + " registered " + registerRequest.getGender(), Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
    RegisterRequest getRegisterRequest(){
        return new RegisterRequest(username.getText().toString(),password.getText().toString(),email.getText().toString(),firstName.getText().toString(),lastName.getText().toString(),gender);
    }
    LoginRequest getLoginRequest(){
        return new LoginRequest(username.getText().toString(),password.getText().toString());
    }
    void request(Requests request){
        try {
            RequestTask task = new RequestTask(request,this);
            URL url= null;
            String baseURL ="http://"+server_host.getText().toString()+":"+server_port.getText().toString();
            if(request instanceof LoginRequest){
                url = new URL(baseURL+"/user/login");
            }
            else if (request instanceof RegisterRequest){
                url = new URL(baseURL+"/user/register");
            }
            URL personUrl = new URL(baseURL+"/person");
            URL eventUrl = new URL(baseURL+"/event");
            task.execute(url,personUrl,eventUrl);
        }
        catch (MalformedURLException e) {
            Log.e("MainActivity", e.getMessage(), e);
        }
    }

    @Override
    public void onError(Error e) {
        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPostExecute(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
