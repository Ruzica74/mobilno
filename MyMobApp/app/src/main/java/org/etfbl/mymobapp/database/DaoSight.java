package org.etfbl.mymobapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.etfbl.mymobapp.model.Sight;

import java.util.List;

@Dao
public interface DaoSight {

    @Query("SELECT * FROM "+Constants.TABLE_NAME_SIGHT)
    List<Sight> getSights();

    @Insert
    long insertSight(Sight sight);

    @Query("DELETE FROM "+ Constants.TABLE_NAME_SIGHT)
    void deleteAll();

    @Update
    int updateSight(Sight s);

}
