package com.example.andrewcruz.parking_app;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {
    Button click;
    public static TextView big_springs_spaces;
    public static TextView lot_6_spaces;
    public static TextView lot_24_spaces;
    public static TextView lot_26_spaces;
    public static TextView lot_30_spaces;
    public static TextView lot_32_spaces;
    public static TextView time;

    private static final int ERROR_DIALOG_REQUEST = 9001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(isServicesOK()) {
            init_bar();
            init_parking();
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
        click = (Button)findViewById(R.id.button);
        big_springs_spaces = (TextView)findViewById(R.id.Big_Springs_Spaces);
        lot_6_spaces = (TextView)findViewById(R.id.Lot_6_Spaces);
        lot_24_spaces = (TextView)findViewById(R.id.Lot_24_Spaces);
        lot_26_spaces = (TextView)findViewById(R.id.Lot_26_Spaces);
        lot_30_spaces = (TextView)findViewById(R.id.Lot_30_Spaces);
        lot_32_spaces = (TextView)findViewById(R.id.Lot_32_Spaces);
        time = (TextView)findViewById(R.id.time);
        fetchData process = new fetchData();
        process.execute();
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchData process = new fetchData();
                process.execute();
                save();
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

    public void save() {
//        SharedPreferences mySp = getSharedPreferences("User_Building_List", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = mySp.edit();

//        String big_springs = big_springs_spaces.getText().toString();
//        String lot_6 = lot_6_spaces.getText().toString();
//        String lot_24 = lot_24_spaces.getText().toString();
//        String lot_26 = lot_26_spaces.getText().toString();
//        String lot_30 = lot_30_spaces.getText().toString();
//        String lot_32 = lot_32_spaces.getText().toString();
//
//        int i1 = big_springs.indexOf('/');
//        int i2 = lot_6.indexOf('/');
//        int i3 = lot_24.indexOf('/');
//        int i4 = lot_26.indexOf('/');
//        int i5 = lot_30.indexOf('/');
//        int i6 = lot_32.indexOf('/');
//
//        big_springs = big_springs.substring(0,i1);
//        lot_6 = lot_6.substring(0,i2);
//        lot_24 = lot_24.substring(0,i3);
//        lot_26 = lot_26.substring(0,i4);
//        lot_30 = lot_30.substring(0,i5);
//        lot_32 = lot_32.substring(0,i6);
//
//        editor.putString("BSP",big_springs );
//        editor.putString("Lot 6", lot_6);
//        editor.putString("Lot 24", lot_24);
//        editor.putString("Lot 26", lot_26);
//        editor.putString("Lot 30", lot_30);
//        editor.putString("Lot 32", lot_32);
//
//        editor.apply();
    }

}



