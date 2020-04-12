package com.shad.familymap;

import android.content.Intent;
import android.os.Build;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;



import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.TreeMap;

import Data.ModelData;
import Models.EventModel;
import Models.PersonModel;


public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback,GoogleMap.OnMarkerClickListener {
    private GoogleMap map;
    private Login.LoginListener listener;
    private EventModel currentEvent;
    private PersonModel currentPerson;
    private ImageView gender;
    private TextView name;
    private TextView eventInfoData;
    private Toolbar mActionBarToolbar;
    private LinearLayout eventLayout;
    static final String PERSON_ID ="com.shad.familymap.person_id";
    private ArrayList<Polyline> lines = new ArrayList<>();
    private ArrayList<Marker> markers = new ArrayList<>();

    MapsFragment(Login.LoginListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
        Objects.requireNonNull(((AppCompatActivity) listener).getSupportActionBar()).setTitle("Family Map");
        setHasOptionsMenu(true);
        eventLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(currentPerson==null){
                    Toast.makeText(getActivity(), "Select an Event", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent((AppCompatActivity) listener, Person.class);
                    intent.putExtra(PERSON_ID, currentPerson.getID());
                    startActivity(intent);
                }
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_item,menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        searchItem.setIcon( new IconDrawable(getActivity(),FontAwesomeIcons.fa_search).sizeDp(30).color(R.color.male));
        MenuItem settingsItem = menu.findItem(R.id.action_settings);
        settingsItem.setIcon( new IconDrawable(getActivity(),FontAwesomeIcons.fa_gear).sizeDp(30).color(R.color.male));
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!ModelData.loggedIn()){
            listener.logout();
        }
        if(ModelData.getCurrentEvent()!=null){
            String currentEvent=ModelData.getCurrentEvent();
            for(Marker i:markers){
                if(i.getTag()==currentEvent){
                    onMarkerClick(i);
                    map.animateCamera(CameraUpdateFactory.newLatLng(i.getPosition()), 250, null);
                }
            }
            ModelData.setCurrentEvent(null);
        }
        else if(map!=null){
            generateEventsOnMap();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent((AppCompatActivity)listener, Settings.class);
                startActivity(intent);
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
        for(Marker i:markers){
            i.remove();
        }
        for(HashMap.Entry<String,EventModel> i: ModelData.getEvents().entrySet()){
            LatLng nextEvent = new LatLng(i.getValue().getLatitude(),i.getValue().getLongitude());
            Marker marker = map.addMarker(new MarkerOptions().position(nextEvent));
            marker.setTag(i.getKey());
            marker.setIcon(BitmapDescriptorFactory.defaultMarker(ModelData.getIconColor(i.getValue().getEventType())));
            markers.add(marker);
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
        FontAwesomeIcons icon =null;
        int color = 0;
        if(currentPerson.getGender().equalsIgnoreCase("m")){
            icon=FontAwesomeIcons.fa_male;
            color = R.color.male;
        }
        else{
            icon=FontAwesomeIcons.fa_female;
            color = R.color.female;
        }
        Drawable genderIcon = new IconDrawable(getActivity(),icon).sizeDp(40).colorRes(color);
        gender.setImageDrawable(genderIcon);
        name.setText(stringName);
        eventInfoData.setText(stringEventInfoDate);
        for(Polyline i :lines){
            i.remove();
        }
        lines.clear();
        if(ModelData.isSpouseLines()){
            EventModel spouseEvent =ModelData.getSpouseFirstEvent(currentEvent.getPersonID());
            if(spouseEvent!=null){
                lines.add(map.addPolyline(new PolylineOptions()
                        .add(
                                new LatLng(currentEvent.getLatitude(), currentEvent.getLongitude()),
                                new LatLng(spouseEvent.getLatitude(),spouseEvent.getLongitude())
                        )
                        .color(ModelData.getLineColor("spouse"))
                ));
            }
        }
        if(ModelData.isFamilyLines()){
            ModelData.getFamilyLines(currentEvent,lines,12,map);
        }
        if(ModelData.isLifeLines()){
            TreeMap<Integer,String> lifeEvents= ModelData.getPersonsEvents(currentEvent.getPersonID());
            EventModel previousEvent = null;
            for(TreeMap.Entry<Integer,String> j:lifeEvents.entrySet()){
                if(previousEvent==null){
                    previousEvent=ModelData.getEvent(j.getValue());
                }
                else{
                    EventModel thisEvent = ModelData.getEvent(j.getValue());
                    lines.add(map.addPolyline(new PolylineOptions()
                            .add(
                                    new LatLng(previousEvent.getLatitude(), previousEvent.getLongitude()),
                                    new LatLng(thisEvent.getLatitude(),thisEvent.getLongitude())
                            )
                            .color(ModelData.getLineColor("life"))
                    ));
                    previousEvent=thisEvent;
                }
            }
        }
        return true;
    }
}
