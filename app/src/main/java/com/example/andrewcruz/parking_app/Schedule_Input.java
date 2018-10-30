package com.example.andrewcruz.parking_app;

import android.content.Intent;
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
//    String with class building names
    ArrayList<String> added_bldgs = new ArrayList<String>();
    ArrayList<String> days_added = new ArrayList<String>();
    ArrayList<String> time_selected = new ArrayList<String>();
    String[] numToDays = {"Monday ", "Tuesday ", "Wednesday ", "Thursday ", "Friday ", "Saturday ", "Sunday "};
//    Array Adapter for listview
    ArrayAdapter<String> arrayAdapter;
    ArrayAdapter<String> arrayAdapter2;
    ArrayAdapter<String> arrayAdapter3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule__input);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        Get Add Building Button from view
        Button bldging_bttn = (Button) findViewById(R.id.add_building);

//        Create the empty list for the classes
        schedule_input_list = (ListView)findViewById(R.id.list_view_schedule);
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_user__class__location__list, R.id.building_name, added_bldgs);
//        arrayAdapter2 = new ArrayAdapter<String>(this, R.layout.activity_user__class__location__list, R.id.building_days, days_added);
//        arrayAdapter3 = new ArrayAdapter<String>(this, R.layout.activity_user__class__location__list, R.id.building_time, time_selected);
        schedule_input_list.setAdapter(arrayAdapter);
//        schedule_input_list.setAdapter(arrayAdapter2);
//        schedule_input_list.setAdapter(arrayAdapter3);

//        On click to move to Building Selection Activity
//        Returns String that provides building selected
        bldging_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Schedule_Input.this, ListViewBuildings.class);
                startActivityForResult(i,1);
            }
        });
    }

//    Used when activity returns result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Schedule_Input.RESULT_OK){
                String result=data.getStringExtra("building_name");
                int res = data.getIntExtra("building_time_hour",0);
                int res2 = data.getIntExtra("building_time_minute",0);
                boolean res3[] = data.getBooleanArrayExtra("building_days_selected");

                String time;
                String days_sel ="";

                for(int i = 0; i < 7; i++) {

                    if(res3[i]) {
                        days_sel += numToDays;
                    }
                }

                time = Integer.toString(res) + Integer.toString(res2);


                added_bldgs.add(result);
                days_added.add(days_sel);
                time_selected.add(time);
                arrayAdapter.notifyDataSetChanged();
//                arrayAdapter2.notifyDataSetChanged();
//                arrayAdapter3.notifyDataSetChanged();
            }
            if (resultCode == Schedule_Input.RESULT_CANCELED) {

            }
        }
    }

}
