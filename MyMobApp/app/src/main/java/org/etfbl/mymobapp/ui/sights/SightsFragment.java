package org.etfbl.mymobapp.ui.sights;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.etfbl.mymobapp.R;
import org.etfbl.mymobapp.database.RepositorySights;
import org.etfbl.mymobapp.model.Sight;
import org.etfbl.mymobapp.ui.activity.SightActivity2;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class SightsFragment extends Fragment implements OnMapReadyCallback {

    //private FragmentSightsBinding binding;
    private SightsViewModel mViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Sight> sights= new ArrayList<>();
    private AdapterSights adapter;
    private Context thisContext;
    private SupportMapFragment mapFragment;
    private GoogleMap mMap;

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap=googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53, -7), 6));

        for (Sight sight:sights) {
            LatLng ll = new LatLng(sight.getGeoDuz(), sight.getGeoSir());
            mMap.addMarker(new MarkerOptions().position(ll).title(sight.getNameEn()));
        }

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // on marker click we are getting the title of our marker
                // which is clicked and displaying it in a toast message.
                //String markerName = marker.getTitle();
                //Toast.makeText(getActivity(), "Clicked location is " + markerName, Toast.LENGTH_SHORT).show();
                try {
                Sight model=null;
                for(Sight sight:sights){
                    if(sight.getNameEn().equals(marker.getTitle()))
                        model=sight;
                }

                String lanf= Locale.getDefault().getLanguage();
                Intent activityIntent = new Intent(thisContext, SightActivity2.class);
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                String json = null;
                json = ow.writeValueAsString(model);
                activityIntent.putExtra("model", json);
                activityIntent.putExtra("language", lanf);
                thisContext.startActivity(activityIntent);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

    }

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
            mapa();
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
        mapFragment= (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map_sight);
        mapFragment.getMapAsync(this);

        // TODO: Use the ViewModel
    }

    private void mapa(){
        mapFragment.getMapAsync(this);
    }
}