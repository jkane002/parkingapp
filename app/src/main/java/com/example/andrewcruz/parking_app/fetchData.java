package com.example.andrewcruz.parking_app;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.firebase.client.Firebase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class fetchData extends AsyncTask<Void, Void, Void> {
    String Big_Springs_Spaces = "";
    String Lot_6_Spaces = "";
    String Lot_24_Spaces = "";
    String Lot_26_Spaces = "";
    String Lot_30_Spaces = "";
    String Lot_32_Spaces = "";

    private String results = "";
    private String results2 = "";
    private String results3 = "";
    private String results4 = "";
    private String results5 = "";
    private String results6 = "";

    String time = "Last Updated: ";

    private Firebase mRef = new Firebase("https://parking-app-222616.firebaseio.com");

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("https://streetsoncloud.com/parking/rest/occupancy/id/84?callback=myCallback");

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                Big_Springs_Spaces = Big_Springs_Spaces + line;
            }

            int index_of_Spaces = Big_Springs_Spaces.indexOf("free_spaces");
            int offset = index_of_Spaces + 18;
            Big_Springs_Spaces = Big_Springs_Spaces.substring(index_of_Spaces, offset);
            results = Big_Springs_Spaces.replaceAll("[^0-9]", "");
            Big_Springs_Spaces = results;


            URL lot6_url = new URL("https://streetsoncloud.com/parking/rest/occupancy/id/238?callback=myCallback");
            HttpURLConnection httpURLConnection_lot_6 = (HttpURLConnection)lot6_url.openConnection();
            InputStream inputStream_lot_6 = httpURLConnection_lot_6.getInputStream();
            BufferedReader bufferedReader_lot_6 = new BufferedReader(new InputStreamReader(inputStream_lot_6));
            String line2 = "";
            while(line2 != null) {
                line2 = bufferedReader_lot_6.readLine();
                Lot_6_Spaces = Lot_6_Spaces + line2;
            }
            int index_of_lot_6_spaces = Lot_6_Spaces.indexOf("free_spaces");
            int offset_lot_6 = index_of_lot_6_spaces + 18;
            Lot_6_Spaces = Lot_6_Spaces.substring(index_of_lot_6_spaces, offset_lot_6);
            results2 = Lot_6_Spaces.replaceAll("[^0-9]", "");
            Lot_6_Spaces = results2;

            URL lot24_url = new URL ("https://streetsoncloud.com/parking/rest/occupancy/id/243?callback=myCallback");
            HttpURLConnection httpURLConnection_lot_24 = (HttpURLConnection)lot24_url.openConnection();
            InputStream inputStream_lot_24 = httpURLConnection_lot_24.getInputStream();
            BufferedReader bufferedReader_lot_24 = new BufferedReader(new InputStreamReader(inputStream_lot_24));
            String line3 = "";
            while(line3 != null) {
                line3 = bufferedReader_lot_24.readLine();
                Lot_24_Spaces = Lot_24_Spaces + line3;
            }
            int index_of_lot_24_spaces = Lot_24_Spaces.indexOf("free_spaces");
            int offset_lot_24 = index_of_lot_24_spaces + 18;
            Lot_24_Spaces = Lot_24_Spaces.substring(index_of_lot_24_spaces, offset_lot_24);
            results3 = Lot_24_Spaces.replaceAll("[^0-9]", "");
            Lot_24_Spaces = results3;


            URL lot26_url = new URL ("https://streetsoncloud.com/parking/rest/occupancy/id/80?callback=myCallback");
            HttpURLConnection httpURLConnection_lot_26 = (HttpURLConnection)lot26_url.openConnection();
            InputStream inputStream_lot_26 = httpURLConnection_lot_26.getInputStream();
            BufferedReader bufferedReader_lot_26 = new BufferedReader(new InputStreamReader(inputStream_lot_26));
            String line4 = "";
            while (line4 != null) {
                line4 = bufferedReader_lot_26.readLine();
                Lot_26_Spaces = Lot_26_Spaces + line4;
            }
            int index_of_lot_26_spaces = Lot_26_Spaces.indexOf("free_spaces");
            int offset_lot_26 = index_of_lot_26_spaces + 18;
            Lot_26_Spaces = Lot_26_Spaces.substring(index_of_lot_26_spaces, offset_lot_26);
            results4 = Lot_26_Spaces.replaceAll("[^0-9]", "");
            Lot_26_Spaces = results4;



            URL lot30_url = new URL ("https://streetsoncloud.com/parking/rest/occupancy/id/82?callback=myCallback");
            HttpURLConnection httpURLConnection_lot_30 = (HttpURLConnection)lot30_url.openConnection();
            InputStream inputStream_lot_30 = httpURLConnection_lot_30.getInputStream();
            BufferedReader bufferedReader_lot_30 = new BufferedReader(new InputStreamReader(inputStream_lot_30));
            String line5 = "";
            while (line5 != null) {
                line5 = bufferedReader_lot_30.readLine();
                Lot_30_Spaces = Lot_30_Spaces + line5;
            }
            int index_of_lot_30_spaces = Lot_30_Spaces.indexOf("free_spaces");
            int offset_lot_30 = index_of_lot_30_spaces + 18;
            Lot_30_Spaces = Lot_30_Spaces.substring(index_of_lot_30_spaces, offset_lot_30);
            results5 = Lot_30_Spaces.replaceAll("[^0-9]", "");
            Lot_30_Spaces = results5;


            URL lot32_url = new URL ("https://streetsoncloud.com/parking/rest/occupancy/id/83?callback=myCallback");
            HttpURLConnection httpURLConnection_lot_32 = (HttpURLConnection)lot32_url.openConnection();
            InputStream inputStream_lot_32 = httpURLConnection_lot_32.getInputStream();
            BufferedReader bufferedReader_lot_32 = new BufferedReader(new InputStreamReader(inputStream_lot_32));
            String line6 = "";
            while (line6 != null) {
                line6 = bufferedReader_lot_32.readLine();
                Lot_32_Spaces = Lot_32_Spaces + line6;
            }
            int index_of_lot_32_spaces = Lot_32_Spaces.indexOf("free_spaces");
            int offset_lot_32 = index_of_lot_32_spaces + 18;
            Lot_32_Spaces = Lot_32_Spaces.substring(index_of_lot_32_spaces, offset_lot_32);
            results6 = Lot_32_Spaces.replaceAll("[^0-9]", "");
            Lot_32_Spaces = results6;

            Date currentTime = Calendar.getInstance().getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss aa");
            time = time + dateFormat.format(currentTime);

            if(Big_Springs_Spaces.isEmpty()) {
                Big_Springs_Spaces = "0";
            }
            if(Lot_6_Spaces.isEmpty()) {
                Big_Springs_Spaces = "0";
            }
            if(Lot_24_Spaces.isEmpty()) {
                Lot_24_Spaces = "0";
            }
            if(Lot_26_Spaces.isEmpty()) {
                Lot_26_Spaces = "0";
            }
            if(Lot_30_Spaces.isEmpty()) {
                Lot_30_Spaces = "0";
            }
            if(Lot_32_Spaces.isEmpty()) {
                Lot_32_Spaces = "0";
            }

        } catch (MalformedParameterizedTypeException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        uploadFirebase();
    }

    private void uploadFirebase() {
        Map<String, Object> map = new HashMap<>();
        map.put( "Big Springs", Integer.parseInt(Big_Springs_Spaces));
        map.put("Lot graph24", Integer.parseInt(Lot_24_Spaces));
        map.put("Lot graph26", Integer.parseInt(Lot_26_Spaces));
        map.put("Lot graph30", Integer.parseInt(Lot_30_Spaces));
        map.put("Lot graph32", Integer.parseInt(Lot_32_Spaces));
        map.put("Lot graph6", Integer.parseInt(Lot_6_Spaces));

        Map<String, Object> m = new HashMap<>();
        Date currentTime = Calendar.getInstance().getTime();
        String time = currentTime.toString();
        m.put("Parse", time);

        mRef.child("Parking").updateChildren(map);
        mRef.child("Triggers").updateChildren(m);

        mRef.child("Data").child("Big Springs").child(time).push().setValue(Big_Springs_Spaces);
        mRef.child("Data").child("Lot 24").child(time).push().setValue(Lot_24_Spaces);
        mRef.child("Data").child("Lot 26").child(time).push().setValue(Lot_26_Spaces);
        mRef.child("Data").child("Lot 30").child(time).push().setValue(Lot_30_Spaces);
        mRef.child("Data").child("Lot 32").child(time).push().setValue(Lot_32_Spaces);
        mRef.child("Data").child("Lot 6").child(time).push().setValue(Lot_6_Spaces);
    }
}