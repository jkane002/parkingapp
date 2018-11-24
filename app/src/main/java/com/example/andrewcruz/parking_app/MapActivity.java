package com.example.andrewcruz.parking_app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;
import java.util.Map;

public class MapActivity extends MainActivity implements OnMapReadyCallback {
//    Google Services
    private GoogleMap mMap;
    Firebase mRef = new Firebase("https://parking-app-222616.firebaseio.com/");
//    Default Values
    private static final float DEFAULT_ZOOM = 16f;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMIT_CODE = 1234;
    private Boolean mLocationPermissionGranted = false;
//  Variables
    Map<String, Object> parkingSpots = new HashMap<>();

    /*
        Instantiates view
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        getLocationPermission();
        initView();
        set_curr_location();
    }


    /*
        Initializes google map
     */
    private void initView() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        init_bar();
        init_spinner();
    }

    /*
        Sets Map to Center of UCR
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        MoveCamera(new LatLng(33.9737, -117.3281), DEFAULT_ZOOM);
    }

    /*
        Initialized bottom navigation bar
     */
    private void init_bar() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_parking:
                        Intent i0 = new Intent(MapActivity.this, MainActivity.class);
                        startActivity(i0);
                        break;

                    case R.id.ic_map:
                        break;

                    case R.id.ic_schedule:
                        Intent i2 = new Intent(MapActivity.this, Schedule_Input.class);
                        startActivity(i2);
                        break;

                    case R.id.ic_info:
                        Intent i3 = new Intent(MapActivity.this, Taps_View.class);
                        startActivity(i3);
                        break;
                }

                return false;
            }
        });
    }

    /*
        Initializes top spinner
     */
    private void init_spinner() {
        Spinner mSpinner;
        mSpinner = (Spinner) findViewById(R.id.parking_lots);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i != 0) {
                    String s = (String) adapterView.getItemAtPosition(i);
                    //
                    geoLocate(s);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(MapActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.parking_lots)) {
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mAdapter);
    }

    /*
        Sets map to User's Current location
     */
    private void set_curr_location() {
        ImageView mGps;
        mGps = (ImageView) findViewById(R.id.ic_gps);
        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDeviceLocation();
            }
        });
    }

    /*
        Sets map and marker to element selected from spinner
     */
    private void geoLocate(String cur) {
        int s1 = (int) getSpots(cur);
        LatLng l = getLatLng(cur);
        MoveCamera(l, DEFAULT_ZOOM, cur, s1);
    }

    /*
        Gets permission for user location
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;

        switch(requestCode) {
            case LOCATION_PERMIT_CODE: {
                if(grantResults.length > 0) {
                    for(int i = 0; i < grantResults.length;i++) {
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    /*
        Gets current USer location
     */
    private void getDeviceLocation() {
        FusedLocationProviderClient mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mLocationPermissionGranted) {
                Task location = mFusedLocationProviderClient.getLastLocation();

                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()) {
//                            SUCCESS
                            Location currentLocation = (Location) task.getResult();
                            MoveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude() ), DEFAULT_ZOOM);
                        } else {
//                            Location Not Found
                            Toast.makeText(MapActivity.this, "Unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e) {
            Log.e("GOOGLE MAPS ACTIVITY",  "Get Device Location: Security Exception: " + e.getMessage());
        }
    }

    /*
        Gets permission from user to access their location.
     */
    private void getLocationPermission() {
        String[] permisssions = {FINE_LOCATION, COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
            } else {
                ActivityCompat.requestPermissions(this, permisssions, LOCATION_PERMIT_CODE);
            }
        }else {
            ActivityCompat.requestPermissions(this, permisssions, LOCATION_PERMIT_CODE);
        }
    }

//Help Functions

    /*
        Adds marker to map given
        @Param:
            l: Latitude and Longitutde of marker placement
            t: Title of marker
            v: Spots Available
     */
    private void addMarker(LatLng l, String t, int v) {
        String s = "Available Spots: ";
        s += parkingText(t,v);

        MarkerOptions options = new MarkerOptions()
                .position(l)
                .title(t)
                .snippet(s);
        Marker mMarker = mMap.addMarker(options);
        mMarker.showInfoWindow();
    }

    /*
        Gets LatLng Object for location key k
        @Param:
            k: The name of the parking lot
        @Return:
            Returns the LatLng object of parking lot
     */
    private LatLng getLatLng(String k) {
        //    Big Springs, Lot 24, Lot 26, Lot 30, Lot 32, Lot 6
        double lat[] = {33.9756387,33.969771,33.9816032,33.9695745,33.969195,33.9780886};
        double log[] = {-117.32097140000002,-117.32745449999999,-117.33491379999998,-117.33264969999999,-117.330363,-117.33056679999999};
        LatLng l;
        switch (k) {
            case "Big Springs":
                l = new LatLng(lat[0],log[0]);
                break;
            case "Lot 6":
                l = new LatLng(lat[1],log[1]);
                break;
            case "Lot 26":
                l = new LatLng(lat[2],log[2]);
                break;
            case "Lot 30":
                l = new LatLng(lat[3],log[3]);
                break;
            case "Lot 32":
                l = new LatLng(lat[4],log[4]);
                break;
            case "Lot 24":
                l = new LatLng(lat[5],log[5]);
                break;
            default:
                l = new LatLng(0,0);
        }
        return l;
    }

    /*
        Returns the parking Text shown on marker
        @Param:
            t: Name of parking lot
            v: Spots Available in parking lot
        @Return:
            String in format "Spots Available/Total Spots"
     */
    private String parkingText(String t, int v) {
        String s = Integer.toString(v);
        switch (t) {
            case "Big Springs":
                s += "/551 spaces";
                break;
            case "Lot 24":
                s += "/384 spaces";
                break;
            case "Lot 26":
                s += "/436 spaces";
                break;
            case "Lot 30":
                s += "/2190 spaces";
                break;
            case "Lot 32":
                s += "/258 spaces";
                break;
            case "Lot 6":
                s += "/329 spaces";
                break;
        }

        return s;
    }

    /*
        Return the number of Parking Spots from key cur
        @Param:
            cur: Parking lot name
        @Return:
            Available spots in cur
     */
    private Object getSpots(String cur) {
        if(cur.equals("Big Springs Structure")) {
            cur = "Big Springs";
        }

        return parkingSpots.get(cur);
    }

    /*
        Used by spinner to instantiate only one marker
        Param:
            latLng: Latitude and Longitude Object
            zoom: Zoom of Map View
            title: Name of Parking Lot
            spots: Available spots in parking lot
     */
    private void MoveCamera(LatLng latLng, float zoom, String title, int spots) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        mMap.clear();

        addMarker(latLng, title, spots);
    }

    /*
        Used by init map to instantiate all markers on screen
        Param:
            latLng: Latitude and Longitude Object
            zoom: Zoom of Map View
     */
    private void MoveCamera(LatLng latLng, float zoom) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        mMap.clear();

        //Get info from firebase and load markers
        getParkingData();

    }
    /*
        Gets the parking data form Firebase and adds marker
        Adds parking lot value into parkingSpots
     */
    private void getParkingData() {
        mRef.child("Parking").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                //Will Dp Nothing Since Set Number of Parking Lots
                int value = dataSnapshot.getValue(int.class);
                String key = dataSnapshot.getKey();
                LatLng l = getLatLng(key);
                addMarker(l,key,value);
                parkingSpots.put(key,value);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //Will Dp Nothing Since Set Number of Parking Lots
                int value = dataSnapshot.getValue(int.class);
                String key = dataSnapshot.getKey();
                LatLng l = getLatLng(key);
                addMarker(l,key,value);
                parkingSpots.put(key,value);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //Will Dp Nothing Since Set Number of Parking Lots
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }
}
