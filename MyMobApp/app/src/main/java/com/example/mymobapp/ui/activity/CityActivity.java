package com.example.mymobapp.ui.activity;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymobapp.R;
import com.example.mymobapp.databinding.ActivityMainBinding;
import com.example.mymobapp.model.City;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

public class CityActivity extends YouTubeBaseActivity {
    private ActivityMainBinding binding;
    private final String API_KEY = "AIzaSyD4VlMBBcciZqcy5JX3Mz1FXwqK8iXpHaE";

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

                String lanf= extras.getString("language");
                System.out.println("Jezik sight activiti: "+lanf);
                if(lanf.equals("en")) {
                    naslov.setText(model.getNameEn());
                    tekst.setText(model.getTextEn());
                }else{
                    naslov.setText(model.getName());
                    tekst.setText(model.getTextSr());
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
                /*TextView actionbarTitle = findViewById(R.id.actionbartitle);
                actionbarTitle.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        CityActivity.super.onBackPressed();
                    }
                });*/
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
}