package com.shad.familymap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import Data.ModelData;
import Models.EventModel;
import Models.Model;
import Models.PersonModel;

public class Search extends AppCompatActivity implements SearchAdapter.ItemClickListener{
    Toolbar mActionBarToolbar;
    SearchView mSearchView;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SearchAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mActionBarToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("Family Map: Search");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSearchView = findViewById(R.id.search);
        recyclerView = (RecyclerView) findViewById(R.id.search_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new SearchAdapter();
        recyclerView.setAdapter(mAdapter);
        mSearchView.setOnQueryTextListener(mAdapter);
        mAdapter.setClickListener(this);
    }

    @Override
    public void onClick(View view, int position) {
        Model model =mAdapter.getItem(position);
        if(model instanceof PersonModel){
            Intent intent = new Intent(this, Person.class);
            intent.putExtra(MapsFragment.PERSON_ID, model.getID());
            startActivity(intent);
        }
        else if(model instanceof EventModel){
            ModelData.setCurrentEvent(model.getID());
            Intent intent = new Intent(this, EventActivity.class);
            startActivity(intent);
        }
    }
}
