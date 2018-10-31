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

import com.example.andrewcruz.parking_app.Buildings;

import java.util.ArrayList;
import java.util.List;

public class BuildingAdapter extends ArrayAdapter<Buildings> {
    private Context mContext;
    private List<Buildings> buildingsList = new ArrayList<>();

    public BuildingAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<Buildings> list) {
        super(context,0,list);
        mContext = context;
        buildingsList = list;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.activity_list_view_buildings,parent,false);

        Buildings currBldg = buildingsList.get(position);
        TextView name = (TextView) listItem.findViewById(R.id.textView);
        name.setText(currBldg.getLocation());

        return listItem;
    }

};
