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


public class Schedule_Input extends AppCompatActivity {
    private static final String TAG = "Schedule Input Activity";
//    ListView simpleList;
//    String[] buildings = {"Chung", "MSE", "BLANK"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule__input);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button bldging_bttn = (Button) findViewById(R.id.add_building);


//        simpleList = (ListView)findViewById(R.id.simpleListView);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, buildings);
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_list_view_buildings, R.id.textView, buildings);
//        simpleList.setAdapter(arrayAdapter);

        bldging_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Schedule_Input.this, ListViewBuildings.class));
//                simpleList = (ListView)findViewById(R.id.building_list);
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, buildings);
//                simpleList.setAdapter(adapter);
            }
        });
    }

}
