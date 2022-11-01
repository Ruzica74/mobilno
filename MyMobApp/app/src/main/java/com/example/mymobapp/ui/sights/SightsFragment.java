package com.example.mymobapp.ui.sights;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymobapp.R;
import com.example.mymobapp.database.RepositoryCity;
import com.example.mymobapp.database.RepositorySights;
import com.example.mymobapp.databinding.FragmentSightsBinding;
import com.example.mymobapp.model.City;
import com.example.mymobapp.model.Sight;
import com.example.mymobapp.ui.city.AdapterCity;
import com.example.mymobapp.ui.city.CityFragment;
import com.example.mymobapp.ui.city.CityViewModel;

import java.util.ArrayList;
import java.util.List;


public class SightsFragment extends Fragment {

    //private FragmentSightsBinding binding;
    private SightsViewModel mViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Sight> sights= new ArrayList<>();
    private AdapterSights adapter;
    private Context thisContext;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /*SightsViewModel sightsViewModel =
                new ViewModelProvider(this).get(SightsViewModel.class);

        binding = FragmentSightsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;*/
        assert container != null;
        this.thisContext = container.getContext();
        return inflater.inflate(R.layout.fragment_sights, container, false);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //binding = null;
    }


    private class GetTask extends AsyncTask<Void, Void, Boolean> {

        private RepositorySights repositorySights;
        private List<Sight> znamenitosti = new ArrayList<>();

        // only retain a weak reference to the activity
        GetTask(RepositorySights r) {
            super();
            repositorySights=r;
        }

        // doInBackground methods runs on a worker thread
        @Override
        protected Boolean doInBackground(Void... objs) {
            znamenitosti = repositorySights.getAll();
            //System.out.println(gradovi.get(1));
            return true;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void onPostExecute(Boolean bool) {
            sights = znamenitosti;
            adapter.setSights(sights);
            //System.out.println("post execute");
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SightsViewModel.class);
        recyclerView = getView().findViewById(R.id.recyclerView2);
        layoutManager = new LinearLayoutManager(thisContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new AdapterSights(sights, thisContext, this.getActivity().getApplication());
        recyclerView.setAdapter(adapter);
        RepositorySights r = new RepositorySights(this.getActivity().getApplication());
        new GetTask(r).execute();


        // TODO: Use the ViewModel
    }
}