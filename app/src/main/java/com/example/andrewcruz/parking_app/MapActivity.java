package com.example.andrewcruz.parking_app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
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
import android.widget.Button;
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
import com.google.maps.android.SphericalUtil;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapActivity extends MainActivity implements OnMapReadyCallback {
//    Google Services
    private GoogleMap mMap;
    Firebase mRef = new Firebase("https://parking-app-222616.firebaseio.com/");
//    Default Values
    private static final float DEFAULT_ZOOM = 14.75f;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMIT_CODE = 1234;
    private Boolean mLocationPermissionGranted = false;
//  Variables
    Map<String, Object> parkingSpots = new HashMap<>();
    ClusterManager<ClusterMarker> mClusterManager;
    private MyClusterMarkerRenderer mClusterManagerRenderer;
    private ArrayList<ClusterMarker> mClusterMarkers = new ArrayList<>();
    boolean parkingClicked = false;
    boolean buildingClicked = false;
    boolean saveParking = false;

    LatLng user = new LatLng(33.976594, -117.328155);

    String buildingName[] = { "Bourns Hall", "Boyce Hall", "CHASS INT NORTH", "CHASS INT SOUTH",
            "CHUNG HALL", "HUMANITIES/SOCIAL SCIENCE", "LIFE SCIENCES", "MATERIAL SCIENCE AND ENGINEERING",
            "OLMSTED HALL", "PHYSICS", "SPROUL HALL", "SKYE", "SURGE FACILITY" , "SPIETH HALL", "THEATER", "UNLH", "WATKINS"};
    String parking[] = {"Big Springs", "Lot 24", "Lot 26", "Lot 30", "Lot 32", "Lot 6"};


    static final String TAG = "MAP ACTIVITY";
    /*
        Instantiates view
        1)Gets Parking Data From Firebase
        2)Makes sure location permissions are allowed
        3)Initializes view
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        getParkingData();
        getLocationPermission();
        initView();
    }

    /*
        Initializes google map
     */
    private void initView() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        init_bar();
        init_spinner();
        init_toggles();
    }

    /*
        Sets Map to Center of UCR
        And Creates Markers for Building and Parking
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        MoveCamera(new LatLng(33.976594, -117.328155), DEFAULT_ZOOM);
        init_markers();
    }

    /*
        Creates markers for Parking and Building
     */
    private void init_markers() {
        addMarkerType("Parking");
        addMarkerType("Building");
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

    private void init_toggles() {
        final ImageView parking = (ImageView) findViewById(R.id.ic_parkingToggle);
        final ImageView building = (ImageView) findViewById(R.id.ic_buildingToggle);
        final ImageView saveLocation = (ImageView) findViewById(R.id.ic_saveParking);

        parking.setClickable(true);
        building.setClickable(true);
        saveLocation.setClickable(true);

        parking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Before Clicked: ", "Parking " + parkingClicked + " Building: " + buildingClicked);
                if(!parkingClicked && buildingClicked) {
                    parkingMarker();
                    mClusterManager.cluster();
                    parkingClicked = true;
                } else if(!parkingClicked && !buildingClicked) {
                    parkingMarker();
                    mClusterManager.cluster();
                    parkingClicked = true;
                } else if (parkingClicked && !buildingClicked){
                    mMap.clear();
                    parkingClicked = false;
                } else {
                    parkingClicked = false;
                }
                Log.d("Clicked: ", "Parking " + parkingClicked + " Building: " + buildingClicked);
            }
        });

        building.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Before Clicked: ", "Parking " + parkingClicked + " Building: " + buildingClicked);
                if(!buildingClicked && parkingClicked) {
                    buildingMarker();
                    mClusterManager.cluster();
                    buildingClicked = true;
                } else if(!buildingClicked && !parkingClicked) {
                    buildingMarker();
                    mClusterManager.cluster();
                    buildingClicked = true;
                } else if (buildingClicked && !parkingClicked){
                    mMap.clear();
                    buildingClicked = false;
                } else {
                    buildingClicked = false;
                }

                Log.d("Clicked: ", "Parking " + parkingClicked + " Building: " + buildingClicked);
            }

        });

        saveLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!saveParking) {
                    addMarkerType("User");
                    saveParking = true;
                    String s = "Parking Location Saved";
                    Toast.makeText(getApplication(),s, Toast.LENGTH_LONG).show();
                } else {
                    String s = "Parking Location removed";
                    Toast.makeText(getApplication(),s, Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Size: " + mClusterMarkers.size());
                    saveParking = false;
                }

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
                    addMarker(s);
//                    if(i >= 1 && i <= 3 )
//                        addMarkerType("Parking");
//                    else
//                        addMarkerType("Building");
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
    private LatLng getDeviceLocation() {
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
                            if(currentLocation != null)
                                user = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
//                            MoveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude() ), DEFAULT_ZOOM);
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
        return user;
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

/*
    Helper Functions
 */

    /*
        Adds marker to map given
        @Param:
            l: Latitude and Longitude of marker placement
            t: Title of marker
            v: Spots Available
     */
    private void addMarker(String s) {
        int icon = R.drawable.ic_parking_icon;
        MoveCamera(getLatLng(s), DEFAULT_ZOOM);
        String snippet = parkingText(s, (int)getSpots(s));
        ClusterMarker newClusterMarker = new ClusterMarker(
                getLatLng(s),
                s,
                snippet,
                icon,
                "Parking"
        );
        mClusterManager.addItem(newClusterMarker);
        mClusterManager.addItem(newClusterMarker);
        mClusterManager.cluster();
        setClosest(newClusterMarker);
    }

    private void addMarkerBuilding(String s) {
        int icon = R.drawable.ic_building_icon;
//        MoveCamera(getLatLng(s), DEFAULT_ZOOM);
        String snippet = "";
        ClusterMarker newClusterMarker = new ClusterMarker(
                getLatLng(s),
                s,
                snippet,
                icon,
                "Building"
        );
        mClusterManager.addItem(newClusterMarker);
        mClusterManager.addItem(newClusterMarker);
        mClusterManager.cluster();
    }

    private void userMarker() {
        int icon = R.drawable.ic_parking_saved_icon;
        LatLng userLocation = getDeviceLocation();
        String snippet = "";
        ClusterMarker newClusterMarker = new ClusterMarker(
                userLocation,
                "Saved Location",
                snippet,
                icon,
                "Building"
        );
        mClusterManager.addItem(newClusterMarker);
        mClusterManager.addItem(newClusterMarker);
        float zoom = 16.0f;
        MoveCamera(userLocation,zoom);
        mClusterManager.cluster();
    }


    /*
        Adds markers by type
     */
    private void addMarkerType(String type) {
        Log.d("addMArkerType", "HERE");
        if(mMap != null) {
            if(mClusterManager == null) {
                mClusterManager = new ClusterManager<ClusterMarker>(this.getApplicationContext(), mMap);

            }
            if(mClusterManagerRenderer == null) {
                mClusterManagerRenderer = new MyClusterMarkerRenderer(
                        this,
                        mMap,
                        mClusterManager
                );
                mClusterManager.setRenderer(mClusterManagerRenderer);
            }
//            Calls Creator for either Parking or Buildings
            switch (type){
                case "Parking":
                    mMap.clear();
                    parkingMarker();
                    break;
                case "Building":
                    mMap.clear();
                    buildingMarker();
                    break;
                case "User":
                    userMarker();
                default:
                    //Do Nothing
                    break;
            }

            mClusterManager.cluster();
        }
    }

    /*
        Creates Markers for ALL Buildings
     */
    private void buildingMarker() {
        MoveCamera(new LatLng(33.976594, -117.328155), DEFAULT_ZOOM);
        int icon = R.drawable.ic_building_icon;

        for (String name: buildingName) {
            String snippet = "";
            ClusterMarker newClusterMarker = new ClusterMarker(
                    getLatLng(name),
                    name,
                    snippet,
                    icon,
                    "Building"
            );
            mClusterManager.addItem(newClusterMarker);
            mClusterManager.addItem(newClusterMarker);
        }
    }

    /*
        Creates Markers for ALL Parking Lots
     */
    private void parkingMarker() {
        MoveCamera(new LatLng(33.976594, -117.328155), DEFAULT_ZOOM);
        int icon = R.drawable.ic_parking_icon;
        String array[] = parking;
        for (String name: array) {
            String title = name;
            String snippet = parkingText(title, (int)getSpots(title));
            ClusterMarker newClusterMarker = new ClusterMarker(
                    getLatLng(name),
                    title,
                    snippet,
                    icon,
                    "Parking"
            );
            mClusterManager.addItem(newClusterMarker);
            mClusterManager.addItem(newClusterMarker);
        }
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
                l = buildingLatLng(k);
        }
        return l;
    }


    /*
        Returns LatLng of Building
     */
    private LatLng buildingLatLng(String buildingName) {
        LatLng l;
        double lat[] = {33.975237, 33.973472, 33.975315, 33.974781, 33.975363, 33.972889,
                33.973791, 33.976243, 33.971593, 33.974693, 33.974048, 33.972862,
                33.975609, 33.975151, 33.972836, 33.976974, 33.975662, 33.972564};

        double log[] = {-117.326946, -117.324890, -117.330572, -117.330470, -117.325963, -117.330692,
                -117.326057, -117.327885, -117.328796, -117.325426, -117.327172, -117.329736,
                -117.328799, -117.328847, -117.326582, -117.337831, -117.328393, -117.329016};


        switch (buildingName) {
            case "Bourns Hall":
                l = new LatLng(lat[0], log[0]);
                break;
            case "Boyce Hall":
                l = new LatLng(lat[1], log[1]);
                break;
            case "CHASS INT NORTH":
                l = new LatLng(lat[2], log[2]);
                break;
            case "CHASS INT SOUTH":
                l = new LatLng(lat[3], log[3]);
                break;
            case "CHUNG HALL":
                l = new LatLng(lat[4], log[4]);
                break;
            case "HUMANITIES/SOCIAL SCIENCE"://
                l = new LatLng(lat[5], log[5]);
                break;
            case "LIFE SCIENCES"://
                l = new LatLng(lat[6], log[6]);
                break;
            case "MATERIAL SCIENCE AND ENGINEERING"://
                l = new LatLng(lat[7], log[7]);
                break;
            case "OLMSTED HALL"://
                l = new LatLng(lat[8], log[8]);
                break;
            case "PHYSICS": //
                l = new LatLng(lat[9], log[9]);
                break;
            case "PIERCE HALL"://
                l = new LatLng(lat[10], log[10]);
                break;
            case "SPROUL HALL"://
                l = new LatLng(lat[11], log[11]);
                break;
            case "SKYE"://
                l = new LatLng(lat[12], log[12]);
                break;
            case "SURGE FACILITY": //
                l = new LatLng(lat[13], log[13]);
                break;
            case "SPIETH HALL": //
                l = new LatLng(lat[14], log[14]);
                break;
            case "THEATER": //
                l = new LatLng(lat[15], log[15]);
                break;
            case "UNLH": //
                l = new LatLng(lat[16], log[16]);
                break;
            case "WATKINS":
                l = new LatLng(lat[17], log[17]);
                break;
            default:
                l = new LatLng(0,0);
                break;
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
            default:
                s+= "";
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
        Used by init map to instantiate all markers on screen
        Param:
            latLng: Latitude and Longitude Object
            zoom: Zoom of Map View
     */
    private void MoveCamera(LatLng latLng, float zoom) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        mMap.clear();
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
                parkingSpots.put(key,value);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //Will Dp Nothing Since Set Number of Parking Lots
                int value = dataSnapshot.getValue(int.class);
                String key = dataSnapshot.getKey();
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

    private void setClosest(ClusterMarker marker) {
        Map<Double, String> unsortMap = new HashMap<>();
        for (String aBuildingName : buildingName) {
            double distance = SphericalUtil.computeDistanceBetween(marker.getPosition(), buildingLatLng(aBuildingName));
            unsortMap.put(distance, aBuildingName);
        }

        Map<Double, String> treeMap = new TreeMap<>(unsortMap);
        int i = 0;
        for (Map.Entry<Double, String> entry : treeMap.entrySet()) {
            if(i >= 5)
                break;
            addMarkerBuilding(entry.getValue());
            i++;
        }
    }

}
