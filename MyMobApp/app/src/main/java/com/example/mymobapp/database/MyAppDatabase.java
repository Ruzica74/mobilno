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
                City city1=new City("Bri", "Bray", "Bray is a coastal town in north County Wicklow, Ireland. It is situated about twenty kilometres (12 mi) south of Dublin city centre on the east coast. It has a population of 32,600 making it the ninth largest urban area within Ireland (at the 2016 census). Bray is home to Ardmore Studios, and some light industry is located in the town, with some business and retail parks on its southern periphery. Commuter links between Bray and Dublin are provided by rail, Dublin Bus and the M11 and M50 motorways. Small parts of the town's northern outskirts are in County Dublin. \n" +
                        "Originally developed as a planned resort town in the 19th century, Bray's popularity as a seaside resort was serviced by the Dublin and Kingstown Railway, which was extended to Bray in 1854. During the late 20th century, the town's use as a resort declined when foreign travel became an option for holiday-makers. However, day-trippers continued to come to Bray during the summer months.\n", "Bri je primorski grad u severnom okrugu Viklou u Irskoj. Nalazi se oko dvadeset kilometara (12 milja) južno od centra Dablina na istočnoj obali. Ima 32.600 stanovnika, što ga čini devetim najvećim urbanim područjem u Irskoj (na popisu iz 2016.). Bri je dom Ardmor studija, a deo lake industrije se nalazi u gradu, sa nekim poslovnim i maloprodajnim parkovima na njegovoj južnoj periferiji. Prigradske veze između Brija i Dablina obezbeđuju se železnicom, Dablinskim autobusom i autoputevima M11 i M50. Mali delovi severne periferije grada su u okrugu Dablin.\n" +
                        "Prvobitno razvijen kao planirano letovalište u 19. veku, popularnost Breja kao primorskog odmarališta opsluživala je železnica Dablina i Kingstauna, koja je proširena na Bri 1854. Tokom kasnog 20. veka, upotreba grada kao odmarališta je opala kada su strani putovanja su postala opcija za turiste. Međutim, izletnici su nastavili da dolaze u Bri tokom letnjih meseci.\n", "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/0b/09/49/14/photo2jpg.jpg?w=700&h=500&s=1", "https://foodandwine.ie/uploads/article/2019/7/2667/bray_beach_GettyImages-908528082_main.jpg","https://www.myirelandtour.com/images/ap/wicklow/bray/bray-town/bray-town-001,lightbox-singular/bray-town-001,ar_unchanged,w_1732,h_1154,.jpg", "https://www.youtube.com/watch?v=kPx-kWh-THk", "","");
                daoc.insertCity(city1);
                System.out.println(daoc.getCities());

            });
        }
    };


}
