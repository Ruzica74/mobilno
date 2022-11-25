package org.etfbl.mymobapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import org.etfbl.mymobapp.model.City;

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
