package com.example.andrewcruz.parking_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class BuildingAdapter extends ArrayAdapter<Buildings> {

    private int resourceLayout;
    private Context mContext;

    public BuildingAdapter(Context context, int resource, List<Buildings> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        Buildings p = getItem(position);


//        Set TextBoxes
        if (p != null) {
            TextView name = (TextView) v.findViewById(R.id.building_name);
            TextView time = (TextView) v.findViewById(R.id.building_time);
            TextView days = (TextView) v.findViewById(R.id.building_days);

//            Set BUilding Name
            if (name != null) {
                name.setText(p.getBuildingName());
            }

//            Set Time
            if (time != null) {
                String t = "";
                String h = Integer.toString(p.getHour());
                String m = Integer.toString(p.getMinute());

                t = h + ":" + m;
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
        }

        return v;
    }

}