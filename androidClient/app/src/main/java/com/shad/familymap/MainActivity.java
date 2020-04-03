package com.shad.familymap;


import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity   implements Login.LoginListener{
    Login loginFragment;
    MapsFragment mMapsFragment;
    private Toolbar mActionBarToolbar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();
        loginFragment = (Login)fm.findFragmentById(R.id.LoginFrameLayout);
        if (loginFragment == null) {
            loginFragment = new Login(this);
            fm.beginTransaction()
                    .add(R.id.LoginFrameLayout, loginFragment)
                    .commit();
        }
        mActionBarToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("Family Map");

    }
    @Override
    public void login() {
        FragmentManager fm = getSupportFragmentManager();
        mMapsFragment = (MapsFragment) fm.findFragmentById(R.id.mapFrameLayout);
        fm.beginTransaction()
                .remove(loginFragment)
                .commit();
        if (mMapsFragment == null) {
            mMapsFragment = new MapsFragment(this);
            Bundle args = new Bundle();
            mMapsFragment.setArguments(args);
            fm.beginTransaction()
                    .add(R.id.mapFrameLayout, mMapsFragment)
                    .commit();
        }
    }

    @Override
    public void changeSubTitle(String newSubTitle) {
        if(getSupportActionBar()==null){
            mActionBarToolbar = findViewById(R.id.toolbar);
            setSupportActionBar(mActionBarToolbar);
        }
        getSupportActionBar().setTitle("Family Map: " +newSubTitle);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
