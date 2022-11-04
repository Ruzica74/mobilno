package com.example.mymobapp.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mymobapp.R;
import com.example.mymobapp.model.Sight;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.picasso.Picasso;

public class CityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            try {
                String json = extras.getString("model");
                ObjectMapper objectMapper = new ObjectMapper();
                Sight model = objectMapper.readValue(json, Sight.class);
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
                Picasso.get().load(model.getPicture1()).into(image1);
                Picasso.get().load(model.getPicture2()).into(image2);
                Picasso.get().load(model.getPicture3()).into(image3);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}