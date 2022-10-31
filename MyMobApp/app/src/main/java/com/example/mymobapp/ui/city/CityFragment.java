package com.example.mymobapp.ui.city;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mymobapp.MainActivity;
import com.example.mymobapp.R;
import com.example.mymobapp.database.RepositoryCity;
import com.example.mymobapp.model.Article;
import com.example.mymobapp.model.City;

import com.example.mymobapp.ui.settings.SettingsFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CityFragment extends Fragment {

    private CityViewModel mViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<City> cities = new ArrayList<>();
    private AdapterCity adapter;
    private Context thisContext;

    private class GetTask extends AsyncTask<Void, Void, Boolean> {

        private RepositoryCity repositoryCity;
        private List<City> gradovi = new ArrayList<>();

        // only retain a weak reference to the activity
        GetTask(RepositoryCity r) {
            super();
            repositoryCity=r;
        }

        // doInBackground methods runs on a worker thread
        @Override
        protected Boolean doInBackground(Void... objs) {
            gradovi = repositoryCity.getAll();
            //System.out.println(gradovi.get(1));
            return true;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void onPostExecute(Boolean bool) {
            cities = gradovi;
            adapter.setCities(cities);
            //System.out.println("post execute");
            adapter.notifyDataSetChanged();
        }
    }

    public static CityFragment newInstance() {
        return new CityFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        assert container != null;
        this.thisContext = container.getContext();
        return inflater.inflate(R.layout.fragment_city, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CityViewModel.class);
        recyclerView = getView().findViewById(R.id.recyclerView1);
        layoutManager = new LinearLayoutManager(thisContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new AdapterCity(cities, thisContext);
        recyclerView.setAdapter(adapter);
        RepositoryCity r = new RepositoryCity(this.getActivity().getApplication());
        new GetTask(r).execute();


        // TODO: Use the ViewModel
    }

}