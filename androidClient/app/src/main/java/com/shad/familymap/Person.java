package com.shad.familymap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;

import Data.ModelData;
import Models.EventModel;
import Models.PersonModel;

public class Person extends AppCompatActivity {
    private PersonModel currentPerson;

    private Toolbar mActionBarToolbar;
    private TextView firstName;
    private TextView lastName;
    private TextView gender;
    private ExpandableListView list;
    private expandableListCustom listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String person_ID = intent.getStringExtra(MapsFragment.PERSON_ID);
        gender= findViewById(R.id.gender);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        getPeople(person_ID);
        mActionBarToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("Family Map: Person Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        list=findViewById(R.id.eventsAndFamily);
        listAdapter= new expandableListCustom(this,currentPerson);
        list.setAdapter(listAdapter);
        list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String childID=(String)(listAdapter.getChild(groupPosition,childPosition));
                if(groupPosition==0){
                    return true;
                }
                else {
                    Intent intent = new Intent(Person.this, Person.class);
                    intent.putExtra(MapsFragment.PERSON_ID, childID);
                    startActivity(intent);
                    return false;
                }

            }
        });
    }

    private void getPeople(String person_id) {
        currentPerson=ModelData.getPerson(person_id);
        firstName.setText(currentPerson.getFirstName());
        lastName.setText(currentPerson.getLastName());
        String genderString;
        if(currentPerson.getGender().equals("f")){
            genderString = "Female";
        }
        else{
            genderString = "Male";
        }
        gender.setText(genderString);
    }
}
