package com.example.andrewcruz.parking_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import android.os.Debug;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
//    TAG for debugging
    private static final String TAG = "Schedule Input Activity";
//    List View Object for USer class list
    ListView schedule_input_list;
    BuildingAdapter userBuildings;
    ArrayList<Buildings> userBuildingList = new ArrayList<Buildings>();
    int listSize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule__input);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        LOAD IF ANY DATA ON FILE
        load();

//        Get Add Building Button from view
        Button bldging_bttn = (Button) findViewById(R.id.add_building);
//        Get Remove Building Button

//        Create the empty list for the classes
        schedule_input_list = (ListView) findViewById(R.id.list_view_schedule);

        userBuildings = new BuildingAdapter(this, R.layout.activity_user__class__location__list, userBuildingList);
        schedule_input_list.setAdapter(userBuildings);


        bldging_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Schedule_Input.this, ListViewBuildings.class);
                startActivityForResult(i,1);
            }
        });
    }

//   Function that handles return from activity after selecting a building location
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
        int k = (timeH + timeH);
        b1.setKey(name.substring(0,3) + Integer.toString(k) + d);
        userBuildingList.add(b1);
        save(b1);
        userBuildings.notifyDataSetChanged();
    }

    protected void save(Buildings b) {
        SharedPreferences mySp = getSharedPreferences("User_Building_List", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySp.edit();
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
        editor.apply();
    }


    protected void load() {
        SharedPreferences mySp = getSharedPreferences("User_Building_List", Context.MODE_PRIVATE);
        listSize = mySp.getInt("List_Size", 0);

        ArrayList<String> keys = new ArrayList<>();
        Map<String, ?> allEntries = mySp.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String comp = entry.getKey().substring(0, entry.getKey().indexOf('_'));
            if(!exist(keys,comp) && !comp.equals("List")) {
                keys.add(comp);
            }
        }


        for(int i = 0; i < keys.size(); i++) {
            String n = mySp.getString(keys.get(i) + "_Name", "N/A");
            int th = mySp.getInt(keys.get(i) + "_TimeH", -1);
            int tm = mySp.getInt(keys.get(i) + "_TimeM", -1);
            String l = mySp.getString("keys.get(i) + _Location_" + i, "N/A");
            boolean d[] = new boolean[7];
            for(int j = 0; j < 7; j++) {
                d[j] = mySp.getBoolean(keys.get(i) + "_Days_" + j, false);
                Log.d("*******************", keys.get(i) +  Boolean.toString(d[j]));
            }

            Buildings b = new Buildings(n,l, th, tm, d);

            userBuildingList.add(b);
            Log.d("KEYS*********", keys.get(i));
        }
    }

    private boolean exist(ArrayList<String> l, String s) {
        for(int i = 0; i < l.size(); i++) {
            if(l.get(i).equals(s))
                return true;
        }
        return false;
    }
}
