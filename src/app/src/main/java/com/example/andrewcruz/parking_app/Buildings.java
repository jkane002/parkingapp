package com.example.andrewcruz.parking_app;

public class Buildings {
        private String building;
        private String location;
        private int time;
//        1 means class on day; 0 means no class on day
        private boolean[] days;

        public Buildings() {
            building = "";
            location = "";
            time = -1;
            days = new boolean[7];
        }

        public Buildings(String b, String l, int t, boolean[] d) {
            building = b;
            location = l;
            time = t;
            days = d;
        }

        public String getLocation() {
            return location;
        }

        public String getBuildingName() {
            return building;
        }

        public int getTime() {
        return time;
    }

        public boolean[] getDays() {return days;}

        public void setLocation(String l) {
            location = l;
        }

        public void setBuildingName(String b) {
            building = b;
        }

        public void setTime(int t) {time = t;}

        public void setDays(int i, boolean new_day) {
            if(i >= 7) {
                return;
            }

            days[i] = new_day;
        }

        public void setDays(boolean[] d) {
            days = d;
        }
}
