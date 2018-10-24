package com.example.andrewcruz.parking_app;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListViewBuildings extends AppCompatActivity {
    ListView simpleList;
    String[] buildings = {"Chung", "MSE", "BLANK"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_buildings);

        simpleList = (ListView)findViewById(R.id.simpleListView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_building__selection, R.id.textView, buildings);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, buildings);
        simpleList.setAdapter(arrayAdapter);
    }
}
