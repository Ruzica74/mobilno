package com.example.mymobapp.database;

import androidx.annotation.RequiresPermission;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mymobapp.model.City;

import java.lang.annotation.Target;
import java.util.List;

@Dao
public interface DaoCity {

    @Query("SELECT * FROM "+Constants.TABLE_NAME_CITY)
    List<City> getCities();

    @Insert
    long insertCity(City city);

    @Query("DELETE FROM "+ Constants.TABLE_NAME_CITY)
    void deleteAll();


}
