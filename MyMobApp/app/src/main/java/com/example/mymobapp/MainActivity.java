package com.example.mymobapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Menu;

import com.example.mymobapp.database.RepositoryCity;
import com.example.mymobapp.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;



import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static final String SELECTED_LANGUAGE = "Locale.Helper.Selected.Language";
    private static final String PREF= "MyStorage";
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    public static String LANGUAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        /*binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_general, R.id.nav_sights, R.id.nav_city, R.id.nav_news, R.id.nav_settings)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        //RepositoryCity r = new RepositoryCity(this.getApplication());
        //new GetTask(r).execute();
    }

    private static class GetTask extends AsyncTask<Void, Void, Boolean> {

        //private WeakReference<AddNoteActivity> activityReference;
        //private Note note;
        private RepositoryCity repositoryCity;

        // only retain a weak reference to the activity
        GetTask(RepositoryCity r) {
            super();
            repositoryCity=r;
        }

        // doInBackground methods runs on a worker thread
        @Override
        protected Boolean doInBackground(Void... objs) {
            System.out.println("gradovi: "+repositoryCity.getAll().get(4));
            return false;
        }

        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool) {
                //DaoCity daoc= myAppDatabase.getDaoCity();
                //System.out.println("gradovi: "+daoc.getCities());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }




    @Override
    protected void attachBaseContext(Context base){
        SharedPreferences shPreferences = PreferenceManager.getDefaultSharedPreferences(base);
        String lang = shPreferences.getString(SELECTED_LANGUAGE, Locale.getDefault().getLanguage());
        LANGUAGE = lang;
        // sacuvamo promjene u konfiguraciji aplikacije
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        base.getResources().updateConfiguration(config,
                base.getResources().getDisplayMetrics());
        super.attachBaseContext(base);
    }
}