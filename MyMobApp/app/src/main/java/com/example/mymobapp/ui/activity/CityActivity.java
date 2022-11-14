package com.example.mymobapp.ui.activity;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymobapp.R;
import com.example.mymobapp.model.City;
import com.example.mymobapp.model.Forecast;
import com.example.mymobapp.model.News;
import com.example.mymobapp.ui.news.NewsFragment;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.Locale;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.content.res.Resources;
import android.content.res.Configuration;

import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CityActivity extends YouTubeBaseActivity {
    private final String API_KEY = "AIzaSyD4VlMBBcciZqcy5JX3Mz1FXwqK8iXpHaE";
    private final String WEATHER_API_KEY= "e0b8d76301849905d7facee5a832f4cd";
    private String language = "";
    private Forecast forecast;


    @SuppressLint("StaticFieldLeak")
    public class OkHttpHandler extends AsyncTask<String, Void, String> {

        OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String... params) {

            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            Request request = builder.build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),"No results", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Gson gson = new Gson();
            forecast = gson.fromJson(s, Forecast.class);
            System.out.println("prognozaa: "+forecast.getWind().getSpeed());
            changeForcast(forecast);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            try {
                String json = extras.getString("model");
                ObjectMapper objectMapper = new ObjectMapper();
                City model = objectMapper.readValue(json, City.class);
                TextView naslov= (TextView) this.findViewById(R.id.city_title);
                TextView tekst = this.findViewById(R.id.city_text);
                ImageView image1 = this.findViewById(R.id.city_image1);
                ImageView image2 = this.findViewById(R.id.city_image2);
                ImageView image3 = this.findViewById(R.id.city_image3);
                TextView pr_grad=this.findViewById(R.id.city_name_weather);

                String lanf= extras.getString("language");
                language = lanf;
                System.out.println("Jezik sight activiti: "+lanf);
                if(lanf.equals("en")) {
                    naslov.setText(model.getNameEn());
                    tekst.setText(model.getTextEn());
                    pr_grad.setText(model.getNameEn());
                }else{
                    naslov.setText(model.getName());
                    tekst.setText(model.getTextSr());
                    pr_grad.setText(model.getName());
                }
                SharedPreferences sh = getSharedPreferences("My App", Context.MODE_PRIVATE);
                int number = sh.getInt("pictures", 2);
                System.out.println("Broj slika: "+number);
                if(number==0)
                {   Picasso.get().load(model.getPicture1()).resize(0, 600).centerCrop().into(image1);}
                else if(number==1)
                {   Picasso.get().load(model.getPicture1()).resize(0, 600).centerCrop().into(image1);
                    Picasso.get().load(model.getPicture2()).resize(0, 600).centerCrop().into(image2);}
                else if(number==2)
                {   Picasso.get().load(model.getPicture1()).resize(0, 600).centerCrop().into(image1);
                    Picasso.get().load(model.getPicture2()).resize(0, 600).centerCrop().into(image2);
                    Picasso.get().load(model.getPicture3()).resize(0, 600).centerCrop().into(image3);}

                YouTubePlayerView youTubePlayerView=findViewById(R.id.player);
                YouTubePlayer.OnInitializedListener listener= new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayer.loadVideo(model.getVideo());
                        youTubePlayer.play();
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                        Toast.makeText(getApplicationContext(), "Failed/Neuspješno", Toast.LENGTH_SHORT).show();
                    }
                };

                youTubePlayerView.initialize(API_KEY, listener);
                TextView actionbarTitle = findViewById(R.id.actionbartitle);
                actionbarTitle.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        CityActivity.super.onBackPressed();
                    }
                });

                OkHttpHandler okHttpHandler = new OkHttpHandler();
                okHttpHandler.execute("https://api.openweathermap.org/data/2.5/weather?q="+model.getNameEn()+"&appid="+WEATHER_API_KEY+"&units=metric");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /*@Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    void changeForcast(Forecast forecast){
        TextView temp = this.findViewById(R.id.temp_text);
        TextView maxmin = this.findViewById(R.id.maxmin_text);
        TextView humidity = this.findViewById(R.id.humidity_text);
        TextView wind = this.findViewById(R.id.wind_text);

        String tmp = forecast.getMain().getTemp()+" °C";
        temp.setText(tmp);
        String mm = forecast.getMain().getTemp_max()+"-"+forecast.getMain().getTemp_min()+" °C";
        maxmin.setText(mm);
        String h = forecast.getMain().getHumidity()+"%";
        humidity.setText(h);
        String w = forecast.getWind().getSpeed()+" km/h";
        wind.setText(w);

    }

}