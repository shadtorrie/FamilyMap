package com.shad.familymap;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;

import Data.ModelData;
import Models.EventModel;
import Models.PersonModel;


public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback,GoogleMap.OnMarkerClickListener {
    private GoogleMap map;
    private Login.LoginListener listener;
    private EventModel currentEvent;
    private PersonModel currentPerson;
    private TextView gender;
    private TextView name;
    private TextView eventInfoData;
    private Toolbar mActionBarToolbar;
    private LinearLayout eventLayout;
    public static final String PERSON_ID ="com.shad.familymap.person_id";

    public MapsFragment(Login.LoginListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, container, savedInstanceState);
        View view = layoutInflater.inflate(R.layout.maps_fragment, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        gender= view.findViewById(R.id.gender);
        name= view.findViewById(R.id.name);
        eventInfoData= view.findViewById(R.id.descriptionAndDate);
        eventLayout = view.findViewById(R.id.EventBar);
        mActionBarToolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)listener).setSupportActionBar(mActionBarToolbar);
        ((AppCompatActivity)listener).getSupportActionBar().setTitle("Family Map");
        setHasOptionsMenu(true);
        eventLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent((AppCompatActivity)listener, Person.class);
                intent.putExtra(PERSON_ID, currentPerson.getID());
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_item,menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        Toast.makeText(getActivity(), "menu", Toast.LENGTH_LONG).show();
        super.onCreateOptionsMenu(menu,inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                //Intent intent = new Intent((AppCompatActivity)listener, Settings.class);
                //startActivity(intent);
                return true;

            case R.id.search:

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMapLoadedCallback(this);
        generateEventsOnMap();
    }

    private void generateEventsOnMap() {
        for(HashMap.Entry<String,EventModel> i: ModelData.getEvents().entrySet()){
            LatLng nextEvent = new LatLng(i.getValue().getLatitude(),i.getValue().getLongitude());
            Marker marker = map.addMarker(new MarkerOptions().position(nextEvent));
            marker.setTag(i.getKey());
            marker.setIcon(BitmapDescriptorFactory.defaultMarker(ModelData.getIconColor(i.getValue().getEventType())));
        }
        map.setOnMarkerClickListener(this);
    }

    @Override
    public void onMapLoaded() {
        // You probably don't need this callback. It occurs after onMapReady and I have seen
        // cases where you get an error when adding markers or otherwise interacting with the map in
        // onMapReady(...) because the map isn't really all the way ready. If you see that, just
        // move all code where you interact with the map (everything after
        // map.setOnMapLoadedCallback(...) above) to here.
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        String eventID =(String)marker.getTag();
        currentEvent= ModelData.getEvent(eventID);
        currentPerson = ModelData.getPerson(currentEvent.getPersonID());
        String stringName =ModelData.getPersonFullName(currentPerson.getID());
        String stringEventInfoDate = ModelData.getEventString(currentEvent.getID());
        gender.setText(currentPerson.getGender());
        name.setText(stringName);
        eventInfoData.setText(stringEventInfoDate);

        return true;
    }
}
