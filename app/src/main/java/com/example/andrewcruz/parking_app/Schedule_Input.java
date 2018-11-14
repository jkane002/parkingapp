package com.example.andrewcruz.parking_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Text;

import com.example.andrewcruz.parking_app.Buildings;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;


public class Schedule_Input extends AppCompatActivity {
    //    List View Object for USer class list
    ListView schedule_input_list;
    //    Custom Building Adapter for Building Class
    BuildingAdapter userBuildings;
    ArrayList<Buildings> userBuildingList = new ArrayList<Buildings>();
    //    Used to keep track of List Size of needed some day
    int listSize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule__input);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);



//        Load preexisting user schedule
        load();
//        Get Add Building Button from view
        Button bldging_bttn = (Button) findViewById(R.id.add_building);
//        Create the empty list for the classes
        schedule_input_list = (ListView) findViewById(R.id.list_view_schedule);
//          Init Custom Building Adapter
        userBuildings = new BuildingAdapter(this, R.layout.activity_user__class__location__list, userBuildingList);
//        Set Adapter
        schedule_input_list.setAdapter(userBuildings);
//        Set OnClick() for Add Building Button in View
        bldging_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Schedule_Input.this, ListViewBuildings.class);
                startActivityForResult(i,1);
            }
        });

        init_bar();
    }

    /*  Function that handles return from activity after selecting a building location
        @Param
            int requestCode: code that determines the request
            int resultCode: code that determines the exiting from previous activity
            Intent data: data passed in from previous activity
        @Return
            void function
    */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Schedule_Input.RESULT_OK){
                String name = data.getStringExtra("building_name");
                int timeH = data.getIntExtra("building_time_hour",0);
                int timeM = data.getIntExtra("building_time_minute",0);
                boolean days[] = data.getBooleanArrayExtra("building_days_selected");
                updateList(name,timeH,timeM,days);
            }
            if (resultCode == Schedule_Input.RESULT_CANCELED) {
//                DO NOTHING
            }
        }
    }

    /*    Function that updates the ListArray<Building> Adapter when new building is selected
          @Param:
            String Name: Building Name
            int timeH: Hour time selected
            int timeM: Minute time selected
            boolean[] days: Days selected, True means class on day
          @Return:
            void
          @Note:
            Include String or Int location in Param when Google maps is integrated
     */
    protected void updateList(String name, int timeH, int timeM, boolean[] days) {
        String location = "NONE FOUND";
        Buildings b1 = new Buildings(name,location,timeH,timeM,days);
        StringBuilder d = new StringBuilder();
        for(int i = 0; i < 7; i++) {
            if(days[i])
                d.append("T");
            else
                d.append("F");
        }
        int k = (timeH + timeM);
        b1.setKey(name.substring(0,3) + Integer.toString(k) + d);
        userBuildingList.add(b1);
        save(b1);
        userBuildings.notifyDataSetChanged();
    }

    /*    Function that saves the building into Shared Prefrences File
          @Param:
            Building b: Building Class Object selected by user
          @Return:
            void
     */
    protected void save(Buildings b) {
//        Get Shared prefs
        SharedPreferences mySp = getSharedPreferences("User_Building_List", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySp.edit();
//        Put Keys and values into Shared Prefs
        editor.putString(b.getKey() + "_Name", b.getBuildingName());
        editor.putInt(b.getKey() + "_TimeH" , b.getHour());
        editor.putInt(b.getKey() + "_TimeM" , b.getMinute());
        editor.putString(b.getKey() + "_Location" , b.getLocation());
        boolean d[] = b.getDays();
        for (int j = 0; j < 7; j++) {
            editor.putBoolean(b.getKey() + "_Days_" + j, d[j]);
        }
        listSize++;
        editor.putInt("List_Size", listSize);
//        Apply Changes
        editor.apply();
    }

    /*    Function that Loads the Current Saved Buildings from Shared Prefs into ListArray<Building> and updates BuildingAdapter
          @Param:
           void
          @Return:
            void
     */
    protected void load() {
//        Get Shared Prefs
        SharedPreferences mySp = getSharedPreferences("User_Building_List", Context.MODE_PRIVATE);
//        Get Building List Size
        listSize = mySp.getInt("List_Size", 0);

//        Get All Elements from Current Shared Prefs
//        Only Save first part of key that corresponds to building
        ArrayList<String> keys = new ArrayList<>();
        Map<String, ?> allEntries = mySp.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if(!check(entry.getKey())) {
                String comp = entry.getKey().substring(0, entry.getKey().indexOf('_'));
                if (!exist(keys, comp) && !comp.equals("List")) {
                    keys.add(comp);
                }
            }
        }
//        Create the building objects to load into ArrayList<Building>
        for(int i = 0; i < keys.size(); i++) {
            String n = mySp.getString(keys.get(i) + "_Name", "N/A");
            int th = mySp.getInt(keys.get(i) + "_TimeH", -1);
            int tm = mySp.getInt(keys.get(i) + "_TimeM", -1);
            String l = mySp.getString("keys.get(i) + _Location", "N/A");
            boolean d[] = new boolean[7];
            for(int j = 0; j < 7; j++) {
                d[j] = mySp.getBoolean(keys.get(i) + "_Days_" + j, false);
            }
//            Create building obj
            Buildings b = new Buildings(n,l, th, tm, d);
            int k = (tm + th);
            StringBuilder dys = new StringBuilder();
            for(int j = 0; j < 7; j++) {
                if(d[j])
                    dys.append("T");
                else
                    dys.append("F");
            }
            String ke = n.substring(0,3) + Integer.toString(k) + dys;
            b.setKey(n.substring(0,3) + Integer.toString(k) + dys);
            userBuildingList.add(b);
        }
    }

    private boolean exist(ArrayList<String> l, String s) {
        for(int i = 0; i < l.size(); i++) {
            if(l.get(i).equals(s))
                return true;
        }
        return false;
    }

    private boolean check(String s) {
        String[] g = {"BSP", "Lot 6","Lot 24", "Lot 26","Lot 30", "Lot 32"};

        for(int i = 0; i < g.length; i++) {
            if(s.equals(g[i]))
                return true;
        }

        return false;
    }

    private void init_bar() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_parking:
                        Intent i0 = new Intent(Schedule_Input.this, MainActivity.class);
                        startActivity(i0);
                        break;

                    case R.id.ic_map:
                        Intent i1 = new Intent(Schedule_Input.this, MapActivity.class);
                        startActivity(i1);
                        break;

                    case R.id.ic_schedule:
                        break;

                    case R.id.ic_info:
                        Intent i3 = new Intent(Schedule_Input.this, Taps_View.class);
                        startActivity(i3);
                        break;
                }

                return false;
            }
        });
    }
}