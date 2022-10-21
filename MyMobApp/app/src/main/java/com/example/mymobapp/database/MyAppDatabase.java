package com.example.mymobapp.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.mymobapp.model.City;
import com.example.mymobapp.model.Sight;


@Database(entities = {City.class, Sight.class}, version=1)
public abstract class MyAppDatabase extends RoomDatabase {


    public abstract DaoSight getDaoSight();

    public static MyAppDatabase myAppDatabase;

    public static synchronized MyAppDatabase getInstance(Context context) {
        if (null == myAppDatabase) {
            myAppDatabase = buildDatabaseInstance(context);
        }
        return myAppDatabase;
    }

    private static MyAppDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                MyAppDatabase.class,
                Constants.DB_NAME).allowMainThreadQueries().build();
    }

    public  void cleanUp(){
        myAppDatabase = null;
    }

}
