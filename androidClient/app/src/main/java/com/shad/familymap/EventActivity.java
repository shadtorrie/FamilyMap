package com.shad.familymap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class EventActivity extends AppCompatActivity implements Login.LoginListener{
    Toolbar mActionBarToolbar;
    MapsFragment mMapsFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        mActionBarToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("Family Map");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FragmentManager fm = getSupportFragmentManager();
        if (mMapsFragment == null) {
            mMapsFragment = new MapsFragment(this);
            Bundle args = new Bundle();
            mMapsFragment.setArguments(args);
        }
        fm.beginTransaction()
                .add(R.id.mapFrameLayout, mMapsFragment)
                .commit();
    }

    @Override
    public void login() {

    }

    @Override
    public void logout() {

    }
}
