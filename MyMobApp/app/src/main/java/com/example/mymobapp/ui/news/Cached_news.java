package com.example.mymobapp.ui.news;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.mymobapp.R;
import com.example.mymobapp.util.Utils;

import java.io.File;

public class Cached_news extends AppCompatActivity {
    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cached_news);
        Bundle extras = getIntent().getExtras();
        String url = extras.getString("url");
        webView = (WebView) findViewById(R.id.webview);
        SharedPreferences sh1 = getSharedPreferences("My App", Context.MODE_PRIVATE);
        Boolean news_cache1 = sh1.getBoolean("cache", true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(webView, url);
                SharedPreferences sh = getSharedPreferences("My App", Context.MODE_PRIVATE);
                Boolean news_cache = sh.getBoolean("cache", true);
                if(news_cache) {
                    File file = new File(getFilesDir(), Utils.getUrlName(url));
                    webView.saveWebArchive(file.getAbsolutePath() + ".mht");
                    System.out.println("Kesiranjeeeeeeeeeeeeeee"+Utils.getUrlName(url));
                }
                //Toast.makeText(getApplicationContext(), "Done!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(getApplicationContext(), "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }
        });
        if(isNetworkConnected()){
            webView.loadUrl(url);
            System.out.println("Normalna verzijaaaaaaaaaaa");
        }else{
            File file = new File(getFilesDir(), Utils.getUrlName(url));
            webView.loadUrl(file.getAbsolutePath() + ".mht");
            System.out.println("Kesirana verzijaaaaaaaaaaa"+Utils.getUrlName(url));
        }

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}