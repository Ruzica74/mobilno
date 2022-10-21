package com.example.mymobapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mymobapp.model.City;
import com.example.mymobapp.model.Sight;

import java.util.List;

@Dao
public interface DaoSight {

    @Query("SELECT * FROM "+Constants.TABLE_NAME1)
    List<Sight> getSights();

    @Insert
    long insertSight(Sight sight);
}
