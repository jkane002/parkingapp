package com.example.andrewcruz.parking_app;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {
        ListView listView;
        private static final int ERROR_DIALOG_REQUEST = 9001;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            if(isServicesOK()) {
                init();
            }
        }


        private void init() {
            listView = (ListView)findViewById(R.id.listView);

            final String[] values = new String[]{"Parking List Web View", "Schedule Input", "TAPS Information", "Map"};
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.activity_list__main,android.R.id.text1, values);

            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) {
                        Intent myintent  = new Intent(view.getContext(), Parking_View.class );
                        startActivityForResult(myintent,0);

                    }
                    if (position == 1) {
                        Intent myintent  = new Intent(view.getContext(), Schedule_Input.class );
                        startActivityForResult(myintent,1);

                    }
                    if (position == 2) {
                        Intent myintent  = new Intent(view.getContext(), Taps_View.class );
                        startActivityForResult(myintent,2);
                    }
                    if (position == 3) {
                        Intent myIntent = new Intent(view.getContext(), MapActivity.class);
                        startActivity(myIntent);
                    }
                }
            });
        }

        public boolean isServicesOK() {
            int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

            if(available == ConnectionResult.SUCCESS) {
                return true;
            }
            else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
                Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
                dialog.show();
            }else {
                Toast.makeText(this, "You can't make map request", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    }



