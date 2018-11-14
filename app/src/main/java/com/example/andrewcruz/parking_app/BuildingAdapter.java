package com.example.andrewcruz.parking_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Debug;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class BuildingAdapter extends ArrayAdapter<Buildings> {

    private int resourceLayout;
    private Context mContext;
    private List<Buildings> b;
    static String TAG = "HELLO";

    public BuildingAdapter(Context context, int resource, List<Buildings> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
        this.b = items;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        Buildings p = getItem(position);
        v.setTag(position);

//        Set TextBoxes
        if (p != null) {
            TextView name = (TextView) v.findViewById(R.id.building_name);
            TextView time = (TextView) v.findViewById(R.id.building_time);
            TextView days = (TextView) v.findViewById(R.id.building_days);
            final Button remove = (Button) v.findViewById(R.id.remove_building);

//            Set BUilding Name
            if (name != null) {
                name.setText(p.getBuildingName());
            }

//            Set Time
            if (time != null) {
                String t = "";
                int hI = p.getHour();
                String h;
                String m;
                if(p.getMinute() < 10) {
                    m = "0" + Integer.toString(p.getMinute());
                }
                else {
                    m = Integer.toString(p.getMinute());
                }
//                Covers 12 13 14 15 16 17 18 19 20 21 22 23
//                       12 1   2  3  4  5  6  7  8  9  10 11
                if(hI >= 12) {
                    if(hI != 12)
                        hI -= 12;
                    h = Integer.toString(hI);
                    t = h + ":" + m + " pm";
                }
//                Cover 0 1 2 3 4 5 6 7 8 9 10 11
//                     12 1 2 3 4 5 6 7 8 9 10 11
                else {
                    if(hI == 0) {
                        hI = 12;
                    }
                    h = Integer.toString(hI);
                    t = h + ":" + m + " am";
                }


                time.setText(t);
            }
//             Set Days
            if (days != null) {
                boolean[] d = p.getDays();
                String day = "";
                String[] sDay = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
                for(int i = 0; i < 7; i++) {
                    if(d[i]) {
                        day += sDay[i];
                        day += " ";
                    }
                }
                days.setText(day);
            }

            //            final ViewGroup par = parent;
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeView(position);
                }
            });
        }




        return v;
    }

    // Method for remove an item of ListView inside adapter class
    // you need to pass as an argument the tag you added to the layout of your choice
    private void removeView(int position ) {
        SharedPreferences mySp = mContext.getSharedPreferences("User_Building_List", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySp.edit();

        editor.remove(b.get(position).getKey() + "_Name");
        editor.remove(b.get(position).getKey() +"_TimeH");
        editor.remove(b.get(position).getKey() +"_TimeM");
        editor.remove(b.get(position).getKey() +"_Location");
        getItem(position).print();
        for(int j = 0; j < 7; j++) {
            editor.remove(b.get(position).getKey() + "_Days_" + j);
        }
        int listSize = mySp.getInt("List_Size", -1);
        listSize--;
        editor.putInt("List_Size", listSize);
        editor.apply();
        Log.d(TAG, "***********************");
        b.remove(position);
        notifyDataSetChanged();
    }

}