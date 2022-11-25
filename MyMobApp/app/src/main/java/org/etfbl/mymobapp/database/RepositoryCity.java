package org.etfbl.mymobapp.database;

import android.app.Application;

import org.etfbl.mymobapp.model.City;

import java.util.List;

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
