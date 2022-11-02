package com.example.mymobapp.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.mymobapp.R;
import com.example.mymobapp.model.Sight;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SightActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sight2);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            try {
                String json = extras.getString("model");
                ObjectMapper objectMapper = new ObjectMapper();
                Sight model = objectMapper.readValue(json, Sight.class);
                //final LayoutInflater factory = getLayoutInflater();
                //final View textEntryView = factory.inflate(R.layout.activity_sight, null);
                TextView naslov= (TextView) this.findViewById(R.id.sight_title);
                naslov.setText(model.getName());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}