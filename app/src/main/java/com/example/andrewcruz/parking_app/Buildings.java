package com.example.andrewcruz.parking_app;

public class Buildings {
        private String building;
        private String location;
        private int hTime;
        private int mTime;
//        1 means class on day; 0 means no class on day
        private boolean[] days;
        private String key;

        public Buildings() {
            building = "EMPTY";
            location = "EMPTY";
            hTime = -1;
            mTime = -1;
            days = new boolean[7];
            key = "EMPTY";
        }

        public Buildings(String b, String l, int th,int tm, boolean[] d) {
            building = b;
            location = l;
            hTime = th;
            mTime = tm;
            days = d;
            key = "EMPTY";
        }

//        Getters
        public String getLocation() {return location;}

        public String getBuildingName() {return building;}

        public int getHour() {return hTime;}

        public int getMinute() {return mTime;}

        public boolean[] getDays() {return days;}

        public String getKey() { return key; }

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
}
