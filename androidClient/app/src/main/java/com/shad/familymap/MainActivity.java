package com.shad.familymap;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;


public class MainActivity extends FragmentActivity implements Login.LoginListener{
    Login loginFragment;
    MapsFragment mMapsFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();
        loginFragment = (Login)fm.findFragmentById(R.id.LoginFrameLayout);
        if (loginFragment == null) {
            loginFragment = new Login(this);
            Bundle args = new Bundle();
            args.putString(loginFragment.ARG_TITLE, "Login");
            loginFragment.setArguments(args);
            fm.beginTransaction()
                    .add(R.id.LoginFrameLayout, loginFragment)
                    .commit();
        }


    }
    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof Login) {
            Login login = (Login) fragment;
            login.setListener(this);
        }
    }
    @Override
    public void enableMap() {
        FragmentManager fm = getSupportFragmentManager();
        mMapsFragment = (MapsFragment) fm.findFragmentById(R.id.mapFrameLayout);
        fm.beginTransaction()
                .remove(loginFragment)
                .commit();
        if (mMapsFragment == null) {
            mMapsFragment = new MapsFragment();
            Bundle args = new Bundle();
            mMapsFragment.setArguments(args);
            fm.beginTransaction()
                    .add(R.id.mapFrameLayout, mMapsFragment)
                    .commit();
        }
    }
}
