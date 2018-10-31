package com.example.andrewcruz.parking_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.w3c.dom.Text;

import com.example.andrewcruz.parking_app.Buildings;

import java.util.ArrayList;


public class Schedule_Input extends AppCompatActivity {
//    TAG for debugging
    private static final String TAG = "Schedule Input Activity";
//    List View Object for USer class list
    ListView schedule_input_list;
    BuildingAdapter userBuildings;
    ArrayList<Buildings> userBuildingList = new ArrayList<Buildings>();

    static String MY_PREFS_NAME = "MyPrefsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule__input);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        Update Building Adapter
//        updateLocalDataSet(userBuildingList);


//        Get Add Building Button from view
        Button bldging_bttn = (Button) findViewById(R.id.add_building);

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
            if (resultCode == Schedule_Input.RESULT_CANCELED) {}
        }
    }

//    Used to update list of buldings automatically after user returns from selecting
    protected void updateList(String name, int timeH, int timeM, boolean[] days) {
//        Create new building;
        String location = "NONE FOUND";
        Buildings b1 = new Buildings(name,location,timeH,timeM,days);
        userBuildingList.add(b1);
        userBuildings.notifyDataSetChanged();

//        updateLocalDataSet(userBuildingList);
    }

//    //Updates local data of
//    protected void updateLocalDataSet(ArrayList<Buildings> userBuild) {
////        if(userBuild != userBuildingList) {
////            Log.d(TAG, "***********NOT THE SAME LIST**************");
////        } else {
////            Log.d(TAG, "***********THE SAME LIST******************");
////        }
//
//        SharedPreferences settings = getApplicationContext().getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
//        SharedPreferences.Editor editor = settings.edit();
//
//        int index = userBuildingList.size() - 1;
//
//        editor.putString("building_name" + index, userBuildingList.get(index).getBuildingName());
//        editor.putString("building_location" + index, userBuildingList.get(index).getLocation());
//        editor.putInt("building_name" + index, userBuildingList.get(index).getHour());
//        editor.putInt("building_name" + index, userBuildingList.get(index).getMinute());
//        editor.putB("building_name" + index, userBuildingList.get(index).getDays());
//
//        editor.apply();
//
//
//    }




}
