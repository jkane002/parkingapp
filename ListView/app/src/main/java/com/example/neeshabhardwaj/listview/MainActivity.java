package com.example.neeshabhardwaj.listview;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    ListView listView;
  //  TextView textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        listView = (ListView)findViewById(R.id.listView);
        listView.setVisibility(View.GONE);

        final String[] values = new String[]{"Schedule Input", "Parking List Web View", "taps Information"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
               R.layout.mytextviewlayout,android.R.id.text1, values);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == 0) {
                    Intent myintent  = new Intent(view.getContext(), Main2Activity.class );
                    startActivityForResult(myintent,0);

                }
                if (position == 1) {
                    Intent myintent  = new Intent(view.getContext(), Main3Activity.class );
                    startActivityForResult(myintent,1);

                }
                if (position == 2) {
                    Intent myintent  = new Intent(view.getContext(), Main2Activity.class );
                    startActivityForResult(myintent,2);
                }
            }
        });
    } //end of on create

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch(menuItem.getItemId()) {

                        case R.id.navigation_schedule:
                            selectedFragment = new ScheduleFragment();
                            break;
                        case R.id.navigation_info:
                            selectedFragment = new InfoFragment();
                            break;
                        case R.id.navigation_home:
                            selectedFragment = new HomeFragment();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
}
