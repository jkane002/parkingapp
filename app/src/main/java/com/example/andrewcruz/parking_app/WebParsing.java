package com.example.andrewcruz.parking_app;

import java.util.TimerTask;


public class WebParsing extends TimerTask {

    @Override
    public void run() {
        fetchData process = new fetchData();
        process.execute();
    }
}
