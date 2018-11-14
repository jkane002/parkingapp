package com.example.andrewcruz.parking_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;



public class Parking_View extends AppCompatActivity {

    Button click;
    public static TextView big_springs_spaces;
    public static TextView lot_6_spaces;
    public static TextView lot_24_spaces;
    public static TextView lot_26_spaces;
    public static TextView lot_30_spaces;
    public static TextView lot_32_spaces;
    public static TextView time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking__view);

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

    public void save() {
        SharedPreferences mySp = getSharedPreferences("User_Building_List", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySp.edit();

        String big_springs = big_springs_spaces.getText().toString();
        String lot_6 = lot_6_spaces.getText().toString();
        String lot_24 = lot_24_spaces.getText().toString();
        String lot_26 = lot_26_spaces.getText().toString();
        String lot_30 = lot_30_spaces.getText().toString();
        String lot_32 = lot_32_spaces.getText().toString();

        int i1 = big_springs.indexOf('/');
        int i2 = lot_6.indexOf('/');
        int i3 = lot_24.indexOf('/');
        int i4 = lot_26.indexOf('/');
        int i5 = lot_30.indexOf('/');
        int i6 = lot_32.indexOf('/');

        big_springs = big_springs.substring(0,i1);
        lot_6 = lot_6.substring(0,i2);
        lot_24 = lot_24.substring(0,i3);
        lot_26 = lot_26.substring(0,i4);
        lot_30 = lot_30.substring(0,i5);
        lot_32 = lot_32.substring(0,i6);

        editor.putString("BSP",big_springs );
        editor.putString("Lot 6", lot_6);
        editor.putString("Lot 24", lot_24);
        editor.putString("Lot 26", lot_26);
        editor.putString("Lot 30", lot_30);
        editor.putString("Lot 32", lot_32);

        editor.apply();
    }

}


