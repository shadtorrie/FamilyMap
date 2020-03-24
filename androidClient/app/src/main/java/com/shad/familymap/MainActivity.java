package com.shad.familymap;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends FragmentActivity {
    Login loginFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = this.getSupportFragmentManager();
        loginFragment = (Login)fm.findFragmentById(R.id.LoginFrameLayout);
        if (loginFragment == null) {
            loginFragment = new Login();
            Bundle args = new Bundle();
            args.putString(loginFragment.ARG_TITLE, "Login");
            loginFragment.setArguments(args);
            fm.beginTransaction()
                    .add(R.id.LoginFrameLayout, loginFragment)
                    .commit();
        }

    }
}
