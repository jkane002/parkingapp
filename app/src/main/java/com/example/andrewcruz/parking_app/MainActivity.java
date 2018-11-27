package com.example.andrewcruz.parking_app;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public TextView big_springs_spaces;
    public TextView lot_6_spaces;
    public TextView lot_24_spaces;
    public TextView lot_26_spaces;
    public TextView lot_30_spaces;
    public TextView lot_32_spaces;
    public TextView time;

    Firebase mRef = new Firebase("https://parking-app-222616.firebaseio.com/");

    private static final int ERROR_DIALOG_REQUEST = 9001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            init_bar();
            init_parking();
            load_firebase();
    }

    public void load_firebase() {
        Log.d("In Load Firebase" ,"*****");
        mRef.child("Parking").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Will Dp Nothing Since Set Number of Parking Lots
                int value = dataSnapshot.getValue(int.class);
                String key = dataSnapshot.getKey();
                Date currentTime = Calendar.getInstance().getTime();
                String time = currentTime.toString();
                setText(value, key, time);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //Will Dp Nothing Since Set Number of Parking Lots
                int value = dataSnapshot.getValue(int.class);
                String key = dataSnapshot.getKey();
                Date currentTime = Calendar.getInstance().getTime();
                String time = currentTime.toString();
                setText(value, key, time);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //Will Dp Nothing Since Set Number of Parking Lots
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void setText(int v, String k, String t) {
        time.setText(t);
        String text;
        switch (k) {
            case "Big Springs":
                text = Integer.toString(v) + "/551 spaces";
                big_springs_spaces.setText(text);
                break;
            case "Lot 24":
                text = Integer.toString(v) + "/384 spaces";
                lot_24_spaces.setText(text);
                break;
            case "Lot 26":
                text = Integer.toString(v) + "/436 spaces";
                lot_26_spaces.setText(text);
                break;
            case "Lot 30":
                text = Integer.toString(v) + "/2190 spaces";
                lot_30_spaces.setText(text);
                break;
            case "Lot 32":
                text = Integer.toString(v) + "/258 spaces";
                lot_32_spaces.setText(text);
                break;
            case "Lot 6":
                text = Integer.toString(v) + "/329 spaces";
                lot_6_spaces.setText(text);
                break;
            default:
                Log.d("SWITCH STATEMENT: ", "NO TextView found");
                break;
        }
    }

    private void init_bar() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_parking:
                        break;

                    case R.id.ic_map:
                        Intent i1 = new Intent(MainActivity.this, MapActivity.class);
                        startActivity(i1);
                        break;

                    case R.id.ic_schedule:
                        Intent i2 = new Intent(MainActivity.this, Schedule_Input.class);
                        startActivity(i2);
                        break;

                    case R.id.ic_info:
                        Intent i3 = new Intent(MainActivity.this, Taps_View.class);
                        startActivity(i3);
                        break;
                }

                return false;
            }
        });

    }

    private void init_parking() {
        ArrayList<TextView> tv = new ArrayList<>();
        big_springs_spaces = (TextView)findViewById(R.id.Big_Springs_Spaces);
        lot_6_spaces = (TextView)findViewById(R.id.Lot_6_Spaces);
        lot_24_spaces = (TextView)findViewById(R.id.Lot_24_Spaces);
        lot_26_spaces = (TextView)findViewById(R.id.Lot_26_Spaces);
        lot_30_spaces = (TextView)findViewById(R.id.Lot_30_Spaces);
        lot_32_spaces = (TextView)findViewById(R.id.Lot_32_Spaces);
        time = (TextView)findViewById(R.id.time);

        Button click = (Button)findViewById(R.id.button);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchData process = new fetchData();
                process.execute();
            }
        });
    }
}



