package com.example.andrewcruz.parking_app;

import android.os.Debug;
import android.util.Log;

public class Buildings {
        private String building;
        private String location;
        private int hTime;
        private int mTime;
//        1 means class on day; 0 means no class on day
        private boolean[] days;
        private String key;
        int image;

        public Buildings() {
            building = "EMPTY";
            location = "EMPTY";
            hTime = -1;
            mTime = -1;
            days = new boolean[7];
            key = "EMPTY";
            image = -1;
        }

        public Buildings(String b, String l, int th,int tm, boolean[] d) {
            building = b;
            location = l;
            hTime = th;
            mTime = tm;
            days = d;
            key = "EMPTY";
            image = -1;
        }

        public Buildings(String b, String l, int th,int tm, boolean[] d,int i) {
            building = b;
            location = l;
            hTime = th;
            mTime = tm;
            days = d;
            key = "EMPTY";
            image = i;
        }

        public Buildings(String b, int i) {
            building = b;
            location = "EMPTY";
            hTime = -1;
            mTime = -1;
            days = new boolean[7];
            key = "EMPTY";
            image = i;
        }


//        Getters
        public String getLocation() {return location;}

        public String getBuildingName() {return building;}

        public int getHour() {return hTime;}

        public int getMinute() {return mTime;}

        public boolean[] getDays() {return days;}

        public String getKey() { return key; }

        public int getImage() {return image;}

//        Setters
        public void setLocation(String l) {
            location = l;
        }

        public void setBuildingName(String b) {
            building = b;
        }

        public void setHour(int h) {
            hTime = h;
        }

        public void setMinute(int m) {
            mTime = m;
        }

        public void setDays(boolean[] d) {
            days = d;
        }

        public void setKey(String k) { key = k; }

        public void setImage(int i) {image = i;}

        public void print() {
            Log.d("************", "Building Class Print: ");
            Log.d("BUILDING:", this.building);
            Log.d("LOCATION:", this.location);
            Log.d("HTIME:", Integer.toString(this.hTime));
            Log.d("MTIME:", Integer.toString(this.mTime));
            for(int i = 0; i < 7; i++)
                Log.d("DAY " + i, Boolean.toString(this.days[i]));
            Log.d("Key:", this.key);
        }

}
