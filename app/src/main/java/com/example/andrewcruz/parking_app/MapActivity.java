package com.example.andrewcruz.parking_app;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;
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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MapActivity extends MainActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final float DEFAULT_ZOOM = 17f;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMIT_CODE = 1234;


    private Marker mMarker;
    private Boolean mLocationPermissionGranted = false;
//    Big Springs, Lot 6, Lot 24, Lot 26, Lot 30, Lot 32
    double lat[] = {33.9756387,33.969771,33.9780886,33.9816032,33.9695745,33.969195};
    double log[] = {-117.32097140000002,-117.32745449999999,-117.33056679999999,-117.33491379999998,-117.33264969999999,-117.330363};

//    Widgets
    private Spinner mSpinner;
    private ImageView mGps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mGps = (ImageView) findViewById(R.id.ic_gps);
        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDeviceLocation();
            }
        });
        getLocationPermission();
        init_spinner();
        init_bar();
    }

    private void init_spinner() {
        mSpinner = (Spinner) findViewById(R.id.parking_lots);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i != 0) {
                    String s = (String) adapterView.getItemAtPosition(i);
                    geoLocate(i - 1, s);
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

    private void geoLocate(int i, String cur) {
        String[] spors = {"BSP", "Lot 6", "Lot 24", "Lot 26", "Lot 30", "Lot 32"};
        SharedPreferences mySp = getSharedPreferences("User_Building_List", Context.MODE_PRIVATE);
        String s = mySp.getString(spors[i], "-1");
        int s1 = Integer.parseInt(s);
        MoveCamera(new LatLng(lat[i],log[i]), DEFAULT_ZOOM, cur, s1);
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is ready", Toast.LENGTH_SHORT).show();
        mMap = googleMap;

        if (mLocationPermissionGranted) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(33.9734, -117.3282);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private void getDeviceLocation() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mLocationPermissionGranted) {
                Task location = mFusedLocationProviderClient.getLastLocation();

                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()) {
//                            SUCCESS
                            Location currentLocation = (Location) task.getResult();
                            MoveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude() ), DEFAULT_ZOOM, "Current Location");
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

    private void MoveCamera(LatLng latLng, float zoom, String title, int spots) {
        Log.d("*********", "HERE");
        Log.d("LatLng", latLng.toString());


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        mMap.clear();

        String snippet = "Available spots: " + Integer.toString(spots);

        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title(title)
                .snippet(snippet);
        mMarker = mMap.addMarker(options);
        mMarker.showInfoWindow();
//        mMap.addMarker(options);
    }


    private void MoveCamera(LatLng latLng, float zoom, String title) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        mMap.clear();
        if(!title.equals("Current Location")) {

            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);

            mMarker = mMap.addMarker(options);
        }
    }

    private void getLocationPermission() {
        String[] permisssions = {FINE_LOCATION, COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permisssions, LOCATION_PERMIT_CODE);
            }
        }else {
            ActivityCompat.requestPermissions(this, permisssions, LOCATION_PERMIT_CODE);
        }
        initMap();
    }

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
                    initMap();
                }
            }
        }
    }

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
}
