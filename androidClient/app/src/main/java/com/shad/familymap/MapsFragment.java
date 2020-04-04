package com.shad.familymap;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
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
    private LinearLayout eventLayout;

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
        eventLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getActivity(), currentPerson.getLastName(), Toast.LENGTH_LONG).show();
            }
        });
        return view;
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
        String stringName =currentPerson.getFirstName()+" "+currentPerson.getLastName();
        String stringEventInfoDate = currentEvent.getEventType()+": "+currentEvent.getCity()+", "+currentEvent.getCountry()+" ("+currentEvent.getYear()+")";
        gender.setText(currentPerson.getGender());
        name.setText(stringName);
        eventInfoData.setText(stringEventInfoDate);

        return true;
    }
}
