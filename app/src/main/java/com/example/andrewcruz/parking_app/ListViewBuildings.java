package com.example.andrewcruz.parking_app;

import android.app.ListActivity;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import java.util.Calendar;
import android.nfc.Tag;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.andrewcruz.parking_app.Buildings;



public class ListViewBuildings extends AppCompatActivity {
    private static final String tag = "ListViewBuilding Act";
    ListView simpleList;
    String[] buildings = {"Bourns Hall", "Boyce Hall", "CHASS INT NORTH", "CHASS INT SOUTH", "CHUNG HALL",
            "HUMANITIES/SOCIAL SCIENCE", "LIFE SCIRENCES", "MATERIAL SCIENCE & ENGINEERING", "OLMSTED HALL",
            "PHYSICS", "PIERCE HALL", "SPROUL HALL", "SKYE", "SURGE FACILITY", "SPIETH HALL", "UV THEATER",
            "UNLH", "WATKINS"};
    String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    boolean[] checked_days = new boolean[] {false, false, false, false, false, false, false};
    int mHour;
    int mMinute;


//    BuildingAdapter bAdapter;
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
                final int p = position;



                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(ListViewBuildings.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(ListViewBuildings.this);
                }

                builder.setTitle("Add Building")
//                .setMessage("What days do you go to this building?")
                        .setMultiChoiceItems(days, checked_days, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                // Update the current focused item's checked status
                                checked_days[which] = isChecked;
                                tiemPicker();
//                        // Get the current focused item
//                        String currentItem = colorsList.get(which);
//                        // Notify the current action
//                        Toast.makeText(getApplicationContext(),
//                                currentItem + " " + isChecked, Toast.LENGTH_SHORT).show();
                            }

                        })
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
//                                tiemPicker();
                                String txt = buildings[p];
                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("building_name", txt);
                                setResult(Schedule_Input.RESULT_OK, returnIntent);
                                finish();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .show();
            }
        });
    }

    private void tiemPicker(){
        final Calendar c = Calendar.getInstance();

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,int minute) {

                        mHour = hourOfDay;
                        mMinute = minute;

//                        et_show_date_time.setText(date_time+" "+hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

}