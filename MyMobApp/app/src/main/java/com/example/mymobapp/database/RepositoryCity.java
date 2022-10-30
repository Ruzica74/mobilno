package com.example.mymobapp.database;

import android.app.Application;
import android.os.AsyncTask;

import com.example.mymobapp.model.City;
import com.example.mymobapp.model.Sight;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class RepositoryCity {
    private  DaoCity daoCity;


    public RepositoryCity(Application application){
        MyAppDatabase myAppDatabase = MyAppDatabase.getInstance(application);
        daoCity = myAppDatabase.getDaoCity();
    }

    public void insert(City city){
        MyAppDatabase.databaseWriteExecutor.execute(()->{
            daoCity.insertCity(city);
        });
    }

    public List<City> getAll(){
            return daoCity.getCities();
    }


}
