package com.shad.familymap;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Objects;

import Data.ModelData;

public class Settings extends AppCompatActivity {
    private Toolbar mActionBarToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mActionBarToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("Family Map: Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Switch lifeStory = (Switch) findViewById(R.id.lifeStorySwitch);
        lifeStory.setChecked(ModelData.isLifeLines());
        lifeStory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ModelData.setLifeLines(isChecked);
            }
        });
        Switch familyTree = (Switch) findViewById(R.id.familyTreeSwitch);
        familyTree.setChecked(ModelData.isFamilyLines());
        familyTree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ModelData.setFamilyLines(isChecked);
            }
        });
        Switch spouseLines = (Switch) findViewById(R.id.spouseSwitch);
        spouseLines.setChecked(ModelData.isFamilyLines());
        spouseLines.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ModelData.setSpouseLines(isChecked);
            }
        });
        Switch fatherSide = (Switch) findViewById(R.id.fathersSwitch);
        fatherSide.setChecked(ModelData.isFamilyLines());
        fatherSide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ModelData.setFathersSide(isChecked);
            }
        });
        Switch motherSide = (Switch) findViewById(R.id.motherSwitch);
        motherSide.setChecked(ModelData.isFamilyLines());
        motherSide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ModelData.setMothersSide(isChecked);
            }
        });
        Switch maleEvents = (Switch) findViewById(R.id.maleSwitch);
        maleEvents.setChecked(ModelData.isFamilyLines());
        maleEvents.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ModelData.setMaleEvents(isChecked);
            }
        });
        Switch femaleEvents = (Switch) findViewById(R.id.femaleEvents);
        femaleEvents.setChecked(ModelData.isFamilyLines());
        femaleEvents.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ModelData.setFemaleEvents(isChecked);
            }
        });
        LinearLayout logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ModelData.logout();
                Settings.this.finish();
            }
        });
    }


}
