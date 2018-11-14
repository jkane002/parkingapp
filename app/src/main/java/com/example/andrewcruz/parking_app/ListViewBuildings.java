package com.example.andrewcruz.parking_app;

import android.app.ListActivity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import java.util.Calendar;
import android.nfc.Tag;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
            "HUMANITIES/SOCIAL SCIENCE", "LIFE SCIENCES", "MATERIAL SCIENCE & ENGINEERING", "OLMSTED HALL",
            "PHYSICS", "PIERCE HALL", "SPROUL HALL", "SKYE", "SURGE FACILITY", "SPIETH HALL", "UV THEATER",
            "UNLH", "WATKINS"};
    String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    boolean[] checked_days = new boolean[] {false, false, false, false, false, false, false};
    int mHour = 12;
    int mMinute = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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


                final AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(ListViewBuildings.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(ListViewBuildings.this);
                }

                View mView = getLayoutInflater().inflate(R.layout.dialog_building,null);

                final CheckBox[] days_checked = {(CheckBox) mView.findViewById(R.id.mon_check),(CheckBox) mView.findViewById(R.id.tues_check),(CheckBox) mView.findViewById(R.id.wed_check),
                        (CheckBox) mView.findViewById(R.id.thur_check), (CheckBox) mView.findViewById(R.id.fri_check), (CheckBox) mView.findViewById(R.id.sat_check),(CheckBox) mView.findViewById(R.id.sun_check)};

                Button set_time = (Button) mView.findViewById(R.id.set_time);
                Button cancel = (Button) mView.findViewById(R.id.cancel_selection);
                Button add = (Button) mView.findViewById(R.id.add_selection);

                set_time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tiemPicker();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                          
                    }
                });

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Set Days that got selected
                        for(int i = 0; i < days_checked.length; i++) {
                            if(days_checked[i].isChecked()) {
                                checked_days[i] = true;
                            }
                        }



                        //Set Building Selected
                        String txt = buildings[p];
                        Intent returnIntent = new Intent();
//                        return building name
                        returnIntent.putExtra("building_name", txt);
//                        return time
                        returnIntent.putExtra("building_time_hour",mHour);
                        returnIntent.putExtra("building_time_minute",mMinute);
//                        return days checked
                        returnIntent.putExtra("building_days_selected",checked_days);

                        setResult(Schedule_Input.RESULT_OK, returnIntent);
                        finish();
                    }
                });

                builder.setView(mView);
                builder.create().show();
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