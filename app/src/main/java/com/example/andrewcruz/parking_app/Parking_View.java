package com.example.andrewcruz.parking_app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
        lot_30_spaces = (TextView)findViewById(R.id.Lot_30_Spaces);;
        lot_32_spaces = (TextView)findViewById(R.id.Lot_32_Spaces);;
        time = (TextView)findViewById(R.id.time);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchData process = new fetchData();
                process.execute();
            }
        });
    }

}


