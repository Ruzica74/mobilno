package com.example.mymobapp.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.mymobapp.model.City;
import com.example.mymobapp.model.Sight;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {City.class, Sight.class}, version=1)
public abstract class MyAppDatabase extends RoomDatabase {


    public abstract DaoSight getDaoSight();
    public abstract DaoCity getDaoCity();

    public static MyAppDatabase myAppDatabase;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

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

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                DaoSight daos= myAppDatabase.getDaoSight();
                daos.deleteAll();

                DaoCity daoc= myAppDatabase.getDaoCity();
                daoc.deleteAll();

                /*Word word = new Word("Hello");
                dao.insert(word);
                word = new Word("World");
                dao.insert(word);*/
            });
        }
    };


}
