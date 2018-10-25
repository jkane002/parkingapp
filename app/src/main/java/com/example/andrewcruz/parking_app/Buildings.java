package com.example.andrewcruz.parking_app;

public class Buildings {
        private String building;
        private String location;
        private int time;

        public Buildings() {
            building = "";
            location = "";
            time = -1;
        }

        public Buildings(String b, String l, int t) {
            building = b;
            location = l;
            time = t;
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

        public void setLocation(String l) {
            location = l;
        }

        public void setBuildingName(String b) {
            building = b;
        }

        public void setTime(int t) {time = t;}
}
