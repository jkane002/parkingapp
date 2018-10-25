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
import com.example.andrewcruz.parking_app.Buildings;



public class ListViewBuildings extends AppCompatActivity {
    private static final String tag = "ListViewBuilding Act";
    ListView simpleList;
    String[] buildings = {"Bourns Hall", "Boyce Hall", "CHASS INT NORTH", "CHASS INT SOUTH", "CHUNG HALL",
            "HUMANITIES/SOCIAL SCIENCE", "LIFE SCIRENCES", "MATERIAL SCIENCE & ENGINEERING", "OLMSTED HALL",
            "PHYSICS", "PIERCE HALL", "SPROUL HALL", "SKYE", "SURGE FACILITY", "SPIETH HALL", "UV THEATER",
            "UNLH", "WATKINS"};

//    Buildings[] blds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        creatArray();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_buildings);

//        Creates List of Building names for list view
        simpleList = (ListView)findViewById(R.id.simpleListView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_building__selection, R.id.textView, buildings);
        simpleList.setAdapter(arrayAdapter);


//        Returns building that was selected after user clicks on it to Schedule_Input activity
        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String txt = buildings[position];

                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",txt);
                setResult(Schedule_Input.RESULT_OK,returnIntent);
                finish();
            }
        });
    }

//    private void creatArray() {
//        blds = new Buildings[building.length];
//
//        for(int i = 0; i < building.length; i++) {
//            blds[i] = new Buildings(building[i], "NO LOCATION SET");
//        }
//    }

}