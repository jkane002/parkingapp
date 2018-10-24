package com.example.andrewcruz.parking_app;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;



public class ListViewBuildings extends AppCompatActivity {
    private static final String tag = "ListViewBuilding Act";
    ListView simpleList;
    String[] buildings = {"Bourns Hall", "Boyce Hall", "CHASS INT NORTH", "CHASS INT SOUTH", "CHUNG HALL",
            "HUMANITIES/SOCIAL SCIENCE", "LIFE SCIRENCES", "MATERIAL SCIENCE & ENGINEERING", "OLMSTED HALL",
            "PHYSICS", "PIERCE HALL", "SPROUL HALL", "SKYE", "SURGE FACILITY", "SPIETH HALL", "UV THEATER",
            "UNLH", "WATKINS"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_buildings);

        simpleList = (ListView)findViewById(R.id.simpleListView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_building__selection, R.id.textView, buildings);
        simpleList.setAdapter(arrayAdapter);

        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String txt = "clicked on " + buildings[position];
                Log.d(tag, txt);
            }
        });
    }
}