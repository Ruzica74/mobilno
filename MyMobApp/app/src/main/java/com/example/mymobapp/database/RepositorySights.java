package com.example.mymobapp.database;

import android.app.Application;

import com.example.mymobapp.model.Sight;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class RepositorySights {

    private DaoSight daoSight;

    RepositorySights(Application application){
        MyAppDatabase myAppDatabase = MyAppDatabase.getInstance(application);
        daoSight = myAppDatabase.getDaoSight();
    }

    void insert(Sight sight){
        MyAppDatabase.databaseWriteExecutor.execute(()->{
            daoSight.insertSight(sight);
        });
    }

    List<Sight> getAll(){
        AtomicReference<List<Sight>> c = null;
        MyAppDatabase.databaseWriteExecutor.execute(()->{
            c.set(daoSight.getSights());
        });
        return c.get();
    }
}
