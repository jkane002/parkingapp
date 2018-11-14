package com.example.jsonp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ParseActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_parse);

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
