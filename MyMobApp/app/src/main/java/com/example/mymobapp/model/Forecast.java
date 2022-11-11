package com.example.mymobapp.model;

import java.io.Serializable;

public class Forecast implements Serializable {

    private Main main;
    private Wind wind;

    public Forecast(Main main, Wind wind) {
        this.main = main;
        this.wind = wind;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }
}
