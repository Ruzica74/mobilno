package org.etfbl.mymobapp.database;

import android.app.Application;

import org.etfbl.mymobapp.model.Sight;

import java.util.List;

public class RepositorySights {

    private DaoSight daoSight;

    public RepositorySights(Application application){
        MyAppDatabase myAppDatabase = MyAppDatabase.getInstance(application);
        daoSight = myAppDatabase.getDaoSight();
    }

    public void insert(Sight sight){
        MyAppDatabase.databaseWriteExecutor.execute(()->{
            daoSight.insertSight(sight);
        });
    }

    public List<Sight> getAll(){
        return daoSight.getSights();
    }

    public boolean update(Sight sight){
        int i= daoSight.updateSight(sight);
        return i != 0;
    }
}
