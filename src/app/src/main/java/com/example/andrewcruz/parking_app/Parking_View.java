package com.example.andrewcruz.parking_app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

public class Parking_View extends AppCompatActivity {

    private WebView webView;
//    TextView textbox1;

    String[] idstoHide = {"header-bg", "footer"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking__view);
        webView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setSupportZoom(false);
        webView.setInitialScale(30);
        webSettings.setDefaultTextEncodingName("utf-8");
//        textbox1=(TextView)findViewById(R.id.textbox1);
        Button but1=(Button)findViewById(R.id.but1);
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTAPS();
                //new doit().execute();
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webView.loadUrl("javascript:(function() { " +
                        "var head = document.getElementsByClassName('header-bg')[0].style.display='none'; " +
                        "})()");
                webView.loadUrl("javascript:(function() { " +
                        "var head = document.getElementsByClassName('footer')[0].style.display='none'; " +
                        "})()");
                webView.loadUrl("javascript:(function() { " +
                        "var head = document.getElementsByClassName('head-logos')[0].style.display='none'; " +
                        "})()");
                webView.loadUrl("javascript:(function() { " +
                        "var head = document.getElementById('viewport').content=" +
                        "width=device-width, initial-scale=0.25, maximum-scale=3.0,minimum-scale=0.25" +
                        "})()");

            }
        });
//        webView.setWebViewClient(new WebViewClient() {
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                return true;
//            }
//        });
        webView.loadUrl("https://parkingapps.ucr.edu/spaces/");

    }
    public void openTAPS() {
        Intent intent = new Intent(this, Taps_View.class);
        startActivity(intent);
    }

    public class doit extends AsyncTask<Void, Void, Void> {
        String words;
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                //    Document doc = Jsoup.connect("https://parkingapps.ucr.edu/spaces/").get();
                //    Element test = doc.getElementsByClass("w2-spaces").first();
                //words = test.text();
                words = "Hi there!";
            }catch(Exception e){e.printStackTrace();}

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);
//            textbox1.setText(words);
        }
    }

}


