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
//    Array Adapter for listview
    ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule__input);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        added_bldgs.add("EMPTY");

//        Get Add Building Button from view
        Button bldging_bttn = (Button) findViewById(R.id.add_building);

//        Create the empty list for the classes
        schedule_input_list = (ListView)findViewById(R.id.list_view_schedule);
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_user__class__location__list, R.id.textView, added_bldgs);
        schedule_input_list.setAdapter(arrayAdapter);

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
                String result=data.getStringExtra("result");
                added_bldgs.add(result);
                arrayAdapter.notifyDataSetChanged();
            }
            if (resultCode == Schedule_Input.RESULT_CANCELED) {

            }
        }
    }

}
