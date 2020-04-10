package com.shad.familymap;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
import android.widget.CompoundButton;
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
    }


}
